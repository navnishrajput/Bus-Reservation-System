package com.bus.main;

import com.bus.model.Booking;
import com.bus.model.Passenger;
import com.bus.service.BusService;
import com.bus.service.FileService;
import com.bus.service.ReportService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BusApp {

    private static FileService fileService = new FileService();
    private static BusService busService = new BusService(fileService);
    private static ReportService reportService = new ReportService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("Welcome to the Core Java Bus Reservation System!");

        while (choice != 0) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Show Available Buses");
            System.out.println("2. Book a Seat");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Daily Report"); // New menu item
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        handleShowBuses(scanner);
                        break;
                    case 2:
                        handleBooking(scanner);
                        break;
                    case 3:
                        handleCancellation(scanner);
                        break;
                    case 4:
                        reportService.printDailyReport(busService.getAllBookings());
                        break;
                    case 0:
                        System.out.println("Thank you for using the Bus Reservation System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the buffer
                choice = -1;
            }
        }
    }

    private static void handleShowBuses(Scanner scanner) {
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String destination = scanner.nextLine();
        busService.showAvailableBuses(source, destination);
    }

    private static void handleBooking(Scanner scanner) {
        try {
            System.out.print("Enter Passenger Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Bus No to book: ");
            String busNo = scanner.nextLine();

            System.out.print("Enter number of seats: ");
            int seatCount = scanner.nextInt();
            scanner.nextLine();

            Passenger passenger = new Passenger(name);

            Booking booking = busService.bookSeat(passenger, busNo, seatCount);
            reportService.printTicket(booking);

        } catch (InputMismatchException e) {
            System.err.println("Invalid number format for seats.");
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("Booking failed: " + e.getMessage());
        }
    }

    private static void handleCancellation(Scanner scanner) {
        try {
            System.out.print("Enter Booking ID to cancel (e.g., R001): ");
            String bookingID = scanner.nextLine();
            busService.cancelBooking(bookingID);
        } catch (Exception e) {
            System.err.println("Cancellation failed: " + e.getMessage());
        }
    }
}