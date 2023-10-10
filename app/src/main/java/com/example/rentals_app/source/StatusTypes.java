package com.example.rentals_app.source;

public enum StatusTypes {

    RENTED("Rented"),
    AVAILABLE("Available"),
    DELETED("Deleted");

    private String name;

    private StatusTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
