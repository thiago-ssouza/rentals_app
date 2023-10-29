package com.example.rentals_app.model;

import java.io.Serializable;
import java.util.List;

public class OwnerModel extends UserModel implements Serializable {

    private List<ApartmentModel> apartments;
    public OwnerModel() {
    }

    public OwnerModel(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
    }

    public OwnerModel(String id, String firstName, String lastName, String email, String phone) {
        super(id, firstName, lastName, email, phone);
    }

    public List<ApartmentModel> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentModel> apartments) {
        this.apartments = apartments;
    }
}