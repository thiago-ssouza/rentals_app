package com.example.rentals_app.source;

public enum LocationTypes {
    CITY_OF_MONTREAL("City of Montreal"),
    NORTH_SHORE("North Shore"),
    SOUTH_SHORE("South Shore"),
    WEST_ISLAND("West Island");

    private String name;

    private LocationTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
