package com.bus.service;

import com.bus.model.Booking;
import java.util.List;

public class ReportService {

    public void printTicket(Booking booking) {
        System.out.println("\n==================================");
        System.out.println("     BUS RESERVATION TICKET     ");
        System.out.println("==================================");
        System.out.println(booking.toString());
        System.out.println("==================================");
    }

    public void printDailyReport(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("\n--- Daily Report ---");
            System.out.println("No bookings recorded yet.");
            return;
        }

        double totalRevenue = 0.0;

        System.out.println("\n=========================================================================");
        System.out.println("                         DAILY BOOKINGS REPORT                           ");
        System.out.println("=========================================================================");
        System.out.printf("%-10s | %-20s | %-7s | %-12s | %-10s%n",
                "ID", "Passenger", "Bus No", "Seats", "Fare (₹)");
        System.out.println("-------------------------------------------------------------------------");

        for (Booking booking : bookings) {
            System.out.printf("%-10s | %-20s | %-7s | %-12s | %-10.2f%n",
                    booking.getBookingID(),
                    booking.getPassengerName(),
                    booking.getBusNo(),
                    booking.getSeatNo(),
                    booking.getTotalFare());

            totalRevenue += booking.getTotalFare();
        }

        System.out.println("=========================================================================");
        System.out.printf("Total Bookings: %d | Total Revenue: ₹%.2f%n", bookings.size(), totalRevenue);
        System.out.println("=========================================================================");
    }
}