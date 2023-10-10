package com.example.rentals_app.source;

public enum LoginTypes {
    TENANT("Tenant"),
    OWNER("Owner");

    private String name;

    private LoginTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
