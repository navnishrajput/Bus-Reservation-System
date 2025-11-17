package com.bus.model;

public class Booking {
    private String bookingID;
    private String passengerName;
    private String busNo;
    private String seatNo; // Use String to store comma-separated seat numbers (e.g., "5,6")
    private double totalFare;

    // Constructor for a new booking
    public Booking(String bookingID, String passengerName, String busNo, String seatNo, double totalFare) {
        this.bookingID = bookingID;
        this.passengerName = passengerName;
        this.busNo = busNo;
        this.seatNo = seatNo;
        this.totalFare = totalFare;
    }

    // --- Getters ---
    public String getBookingID() { return bookingID; }
    public String getPassengerName() { return passengerName; }
    public String getBusNo() { return busNo; }
    public String getSeatNo() { return seatNo; }
    public double getTotalFare() { return totalFare; }

    // --- Utility Method ---
    public String toCSVString() {
        return String.format("%s,%s,%s,%s,%.2f",
                bookingID, passengerName, busNo, seatNo, totalFare);
    }

    // Override toString for easy ticket printing
    @Override
    public String toString() {
        return "\n*** TICKET CONFIRMED ***" +
                "\nBooking ID: " + bookingID +
                "\nPassenger: " + passengerName +
                "\nBus No: " + busNo +
                "\nSeats: " + seatNo +
                "\nTotal Fare: $" + String.format("%.2f", totalFare);
    }
}