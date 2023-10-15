package com.example.rentals_app.model;

import java.io.Serializable;

public class OwnerModel extends UserModel implements Serializable {

    public OwnerModel() {
    }

    public OwnerModel(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
    }
}