package com.bus.model;

public class Passenger {
    private String name;
    // You might add contact number, email, etc.

    public Passenger(String name) {
        this.name = name;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }
}