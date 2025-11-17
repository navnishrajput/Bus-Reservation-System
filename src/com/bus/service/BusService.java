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
        this.buses = fileService.readBusesFromCSV();
        this.bookings = fileService.readBookingsFromCSV();

        // Initialize nextBookingID based on existing bookings
        if (!bookings.isEmpty()) {
            int maxId = bookings.stream()
                    .map(b -> b.getBookingID().substring(1)) // Remove "B" or "R" prefix
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
            nextBookingID = maxId + 1;
        }
    }

    public List<Bus> getAllBuses() {
        return this.buses;
    }

    public List<Booking> getAllBookings() {
        return this.bookings;
    }

    public void showAvailableBuses(String source, String destination) {
        List<Bus> available = buses.stream()
                .filter(b -> b.getSource().equalsIgnoreCase(source) &&
                        b.getDestination().equalsIgnoreCase(destination) &&
                        b.getSeatsAvailable() > 0)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            System.out.println("\nNo buses available for this route.");
            return;
        }

        System.out.println("\n--- Available Buses ---");
        available.forEach(System.out::println);
        System.out.println("-----------------------");
    }

    public Booking bookSeat(Passenger passenger, String busNo, int seatCount) throws Exception {
        Bus selectedBus = buses.stream()
                .filter(b -> b.getBusNo().equalsIgnoreCase(busNo))
                .findFirst()
                .orElseThrow(() -> new Exception("Bus not found with number: " + busNo));

        if (selectedBus.getSeatsAvailable() < seatCount) {
            throw new Exception("Booking failed: Only " + selectedBus.getSeatsAvailable() + " seats available.");
        }

        // 1. Calculate fare
        double totalFare = selectedBus.getFare() * seatCount;

        // 2. Generate seat numbers (Simplified: just assign continuous seat numbers)
        String seatNumbers = generateSeatNumbers(selectedBus, seatCount);

        // 3. Create Booking
        String bookingID = "R" + String.format("%03d", nextBookingID++);
        Booking newBooking = new Booking(bookingID, passenger.getName(), busNo, seatNumbers, totalFare);

        // 4. Update Bus state (Crucial step)
        selectedBus.setSeatsAvailable(selectedBus.getSeatsAvailable() - seatCount);

        // 5. Update files (Transaction completion)
        fileService.appendBookingToCSV(newBooking);
        fileService.writeBusesToCSV(buses); // Overwrite the whole list with updated seats

        bookings.add(newBooking); // Update in-memory list

        return newBooking;
    }

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

    public void cancelBooking(String bookingID) throws Exception {
        Booking bookingToRemove = bookings.stream()
                .filter(b -> b.getBookingID().equalsIgnoreCase(bookingID))
                .findFirst()
                .orElseThrow(() -> new Exception("Booking ID not found: " + bookingID));

        // 1. Find the bus and determine seats cancelled
        Bus affectedBus = buses.stream()
                .filter(b -> b.getBusNo().equals(bookingToRemove.getBusNo()))
                .findFirst()
                .orElseThrow(() -> new Exception("Associated bus not found. Data inconsistency."));

        // Count seats from the comma-separated string
        int cancelledSeats = bookingToRemove.getSeatNo().split(",").length;

        // 2. Return seats to the bus
        affectedBus.setSeatsAvailable(affectedBus.getSeatsAvailable() + cancelledSeats);

        // 3. Remove the booking from the in-memory list
        bookings.remove(bookingToRemove);

        // 4. Update files
        fileService.writeBookingsToCSV(bookings);
        fileService.writeBusesToCSV(buses);

        System.out.println("Cancellation successful for Booking ID: " + bookingID);
    }
}