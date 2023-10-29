package com.example.rentals_app.model;

import java.io.Serializable;
import java.util.List;

public class OwnerModel extends UserModel implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    public class OwnerModel extends UserModel implements Serializable {

        public OwnerModel() {
        }

        public OwnerModel(String firstName, String lastName, String email, String phone) {
            super(firstName, lastName, email, phone);
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "OwnerModel{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
    private List<ApartmentModel> apartments;


    public OwnerModel(String id, String firstName, String lastName, String email, String phone) {
    }

    public List<ApartmentModel> getApartments() {
        return apartments;
    }

    public void setApartments(List<ApartmentModel> apartments) {
        this.apartments = apartments;
    }
}