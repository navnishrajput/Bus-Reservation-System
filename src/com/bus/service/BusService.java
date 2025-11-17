package com.bus.service;

import com.bus.model.Bus;
import com.bus.model.Booking;
import com.bus.model.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class BusService {
    private List<Bus> buses;
    private List<Booking> bookings;
    private final FileService fileService;
    private int nextBookingID = 1;

    public BusService(FileService fileService) {
        this.fileService = fileService;
        // Load data on startup
        this.buses = fileService.readBusesFromCSV();
        this.bookings = fileService.readBookingsFromCSV();

        // Determine the next starting booking ID based on existing data
        if (!bookings.isEmpty()) {
            int maxId = bookings.stream()
                    .map(b -> b.getBookingID().substring(1)) // Remove prefix (e.g., 'R')
                    .mapToInt(s -> {
                        try { return Integer.parseInt(s); }
                        catch (NumberFormatException e) { return 0; } // Handle invalid IDs gracefully
                    })
                    .max()
                    .orElse(0);
            nextBookingID = maxId + 1;
        }
    }

    // Getter for the main application to use for reports
    public List<Booking> getAllBookings() {
        return this.bookings;
    }

    // Displays buses matching source/destination and having available seats
    public void showAvailableBuses(String source, String destination) {
        List<Bus> available = buses.stream()
                .filter(b -> b.getSource().equalsIgnoreCase(source) &&
                        b.getDestination().equalsIgnoreCase(destination) &&
                        b.getSeatsAvailable() > 0)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            System.out.println("\nNo buses available for the route " + source + " -> " + destination);
            return;
        }

        System.out.println("\n--- Available Buses ---");
        available.forEach(System.out::println);
        System.out.println("-----------------------");
    }

    // Handles the core booking logic (transaction)
    public Booking bookSeat(Passenger passenger, String busNo, int seatCount) throws Exception {
        // OOPs: Finding the bus object
        Bus selectedBus = buses.stream()
                .filter(b -> b.getBusNo().equalsIgnoreCase(busNo))
                .findFirst()
                .orElseThrow(() -> new Exception("Bus not found with number: " + busNo));

        // Exception Handling: Check availability
        if (selectedBus.getSeatsAvailable() < seatCount) {
            throw new Exception("Booking failed: Only " + selectedBus.getSeatsAvailable() + " seats available.");
        }

        // 1. Calculate fare
        double totalFare = selectedBus.getFare() * seatCount;

        // 2. Generate seat numbers
        String seatNumbers = generateSeatNumbers(selectedBus, seatCount);

        // 3. Create Booking object
        String bookingID = "R" + String.format("%03d", nextBookingID++);
        Booking newBooking = new Booking(bookingID, passenger.getName(), busNo, seatNumbers, totalFare);

        // 4. Update Bus state (Crucial)
        selectedBus.setSeatsAvailable(selectedBus.getSeatsAvailable() - seatCount);

        // 5. File I/O: Update persistent storage
        fileService.appendBookingToCSV(newBooking);
        fileService.writeBusesToCSV(buses);

        bookings.add(newBooking); // Update in-memory list

        return newBooking;
    }

    // Calculates which seats were booked (simplified)
    private String generateSeatNumbers(Bus bus, int count) {
        int seatsBooked = bus.getTotalSeats() - bus.getSeatsAvailable();
        int startSeat = seatsBooked + 1;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++) {
            sb.append(startSeat + i);
            if(i < count - 1) sb.append(",");
        }
        return sb.toString();
    }

    // Handles the core cancellation logic
    public void cancelBooking(String bookingID) throws Exception {
        // OOPs: Find booking object
        Booking bookingToRemove = bookings.stream()
                .filter(b -> b.getBookingID().equalsIgnoreCase(bookingID))
                .findFirst()
                .orElseThrow(() -> new Exception("Booking ID not found: " + bookingID));

        // Find associated bus
        Bus affectedBus = buses.stream()
                .filter(b -> b.getBusNo().equals(bookingToRemove.getBusNo()))
                .findFirst()
                .orElseThrow(() -> new Exception("Associated bus not found. Data inconsistency."));

        // Count seats
        int cancelledSeats = bookingToRemove.getSeatNo().split(",").length;

        // 1. Return seats to the bus
        affectedBus.setSeatsAvailable(affectedBus.getSeatsAvailable() + cancelledSeats);

        // 2. Remove the booking from the in-memory list
        bookings.remove(bookingToRemove);

        // 3. File I/O: Update persistent storage
        fileService.writeBookingsToCSV(bookings); // Rewrites without the cancelled booking
        fileService.writeBusesToCSV(buses);

        System.out.println("Cancellation successful for Booking ID: " + bookingID);
    }
}