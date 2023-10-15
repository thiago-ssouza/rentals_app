package com.example.rentals_app.model;

import java.io.Serializable;

public class TenantModel extends UserModel implements Serializable {

    public TenantModel() {
    }

    public TenantModel(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
    }
}
