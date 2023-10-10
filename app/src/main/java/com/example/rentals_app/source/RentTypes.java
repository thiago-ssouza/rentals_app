package com.example.rentals_app.source;

public enum RentTypes {
    LONG_TERM_RENTALS("Long Term Rentals"),
    SHORT_TERM_RENTALS("Short Term Rentals"),
    ROOM_RENTALS_AND_ROOMMATES("Room Rentals and Roommates");

    private String name;

    private RentTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}