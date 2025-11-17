package com.bus.model;

public class Bus {
    private String busNo;
    private String source;
    private String destination;
    private int totalSeats;
    private int seatsAvailable;
    private double fare;

    // Constructor to create a Bus object from CSV data
    public Bus(String busNo, String source, String destination, int totalSeats, int seatsAvailable, double fare) {
        this.busNo = busNo;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.seatsAvailable = seatsAvailable;
        this.fare = fare;
    }

    // --- Getters ---
    public String getBusNo() { return busNo; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public int getTotalSeats() { return totalSeats; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public double getFare() { return fare; }

    // --- Setters (Essential for updating seats) ---
    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    // --- Utility Method ---
    public String toCSVString() {
        return String.format("%s,%s,%s,%d,%d,%.2f",
                busNo, source, destination, totalSeats, seatsAvailable, fare);
    }

    // Override toString for easy display
    @Override
    public String toString() {
        return String.format("Bus No: %s | Route: %s -> %s | Available Seats: %d | Fare: $%.2f",
                busNo, source, destination, seatsAvailable, fare);
    }
}