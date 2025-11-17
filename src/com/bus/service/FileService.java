package com.bus.service;

import com.bus.model.Bus;
import com.bus.model.Booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String BUSES_FILE = "buses.csv";
    private static final String BOOKINGS_FILE = "bookings.csv";

    // --- Helper for Booking Header ---
    private static final String BOOKING_HEADER = "BookingID,PassengerName,BusNo,SeatNo,TotalFare";

    // Method to load all buses from buses.csv
    public List<Bus> readBusesFromCSV() {
        List<Bus> buses = new ArrayList<>();
        // Uses try-with-resources for automatic resource closing (File I/O)
        try (BufferedReader br = new BufferedReader(new FileReader(BUSES_FILE))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    try {
                        Bus bus = new Bus(
                                data[0].trim(),           // BusNo
                                data[1].trim(),           // Source
                                data[2].trim(),           // Destination
                                Integer.parseInt(data[3].trim()), // TotalSeats
                                Integer.parseInt(data[4].trim()), // SeatsAvailable
                                Double.parseDouble(data[5].trim())// Fare
                        );
                        buses.add(bus);
                    } catch (NumberFormatException e) {
                        // Exception Handling for corrupted data
                        System.err.println("Error parsing number in buses.csv line: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("CRITICAL: 'buses.csv' not found. Ensure it is in the project root.");
        } catch (IOException e) {
            System.err.println("Error reading bus data: " + e.getMessage());
        }
        return buses;
    }

    // Method to load all bookings from bookings.csv
    public List<Booking> readBookingsFromCSV() {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    try {
                        Booking booking = new Booking(
                                data[0].trim(),           // BookingID
                                data[1].trim(),           // PassengerName
                                data[2].trim(),           // BusNo
                                data[3].trim(),           // SeatNo
                                Double.parseDouble(data[4].trim())// TotalFare
                        );
                        bookings.add(booking);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number in bookings.csv line: " + line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("INFO: 'bookings.csv' not found. Starting with empty bookings list.");
        } catch (IOException e) {
            System.err.println("Error reading booking data: " + e.getMessage());
        }
        return bookings;
    }

    // Method to write the full list of updated buses back to the file
    public void writeBusesToCSV(List<Bus> buses) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BUSES_FILE))) {
            pw.println("BusNo,Source,Destination,TotalSeats,SeatsAvailable,Fare");
            for (Bus bus : buses) {
                pw.println(bus.toCSVString());
            }
        } catch (IOException e) {
            System.err.println("Error writing bus data: " + e.getMessage());
        }
    }

    // Method to append a single new booking
    public void appendBookingToCSV(Booking booking) {
        try {
            // Check if file exists to decide whether to write header
            boolean fileExists = new File(BOOKINGS_FILE).exists();

            // Use 'true' for FileWriter to enable APPEND mode
            try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE, true))) {
                // If file didn't exist, write the header first
                if (!fileExists || new File(BOOKINGS_FILE).length() == 0) {
                    pw.println(BOOKING_HEADER);
                }
                pw.println(booking.toCSVString());
            }
        } catch (IOException e) {
            System.err.println("Error appending booking data: " + e.getMessage());
        }
    }

    // Method to rewrite the full list of bookings (used for cancellation)
    public void writeBookingsToCSV(List<Booking> bookings) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            pw.println(BOOKING_HEADER);
            for (Booking booking : bookings) {
                pw.println(booking.toCSVString());
            }
        } catch (IOException e) {
            System.err.println("Error rewriting booking data after cancellation: " + e.getMessage());
        }
    }
}