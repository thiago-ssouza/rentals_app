package com.example.rentals_app.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String id;
    public String firstName;
    public String lastName;
    public String email;
    private String phone;
    private static UserModel loggedUser = null;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public static boolean setSession(UserModel userModel)
    {
        if (loggedUser == null) {
            loggedUser = userModel;

            return true;
        }

        return false;
    }

    public static UserModel getSession()
    {
        if (loggedUser == null)
            return null;

        return loggedUser;
    }

    public static boolean closeSession()
    {
        if (loggedUser == null)
            return false;

        loggedUser = null;

        return true;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String dataDb() {

        return "UserModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "ID='" + id + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
