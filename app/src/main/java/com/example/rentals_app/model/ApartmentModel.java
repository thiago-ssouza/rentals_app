package com.example.rentals_app.model;

import com.example.rentals_app.source.LocationTypes;
import com.example.rentals_app.source.RentTypes;
import com.example.rentals_app.source.StatusTypes;

import java.io.Serializable;
import java.util.List;

public class ApartmentModel implements Serializable {

    private String id;
    private int unitNumber;
    private String address;
    private String postalCode;
    private LocationTypes location;
    private int size;
    private int bedrooms;
    private int bathrooms;
    private boolean hasParking;
    private boolean hasHeating;
    private int stageFloor;
    private RentTypes rentType;
    private double price;
    private String title;
    private String description;
    private OwnerModel owner;
    private StatusTypes status;
    private List<String> images;
    private List<MessageModel> messages;
  
    public ApartmentModel() {
    }


    public ApartmentModel(String id, String address, LocationTypes location, double price, String title) {
        this.id = id;
        this.address = address;
        this.location = location;
        this.price = price;
        this.title = title;
    }

    public ApartmentModel(int unitNumber, String address, String postalCode, LocationTypes location,
                          int size, int bedrooms, int bathrooms, boolean hasParking,
                          boolean hasHeating, int stageFloor, RentTypes rentType, double price,
                          String title, String description, OwnerModel owner, StatusTypes status,
                          List<String> images, List<MessageModel> messages) {
        this.unitNumber = unitNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.location = location;
        this.size = size;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.hasParking = hasParking;
        this.hasHeating = hasHeating;
        this.stageFloor = stageFloor;
        this.rentType = rentType;
        this.price = price;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.status = status;
        this.images = images;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocationTypes getLocation() {
        return location;
    }

    public void setLocation(LocationTypes location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }


    public boolean getHasParking() {
        return hasParking;
    }

    public void setHasParking(boolean hasParking) {
        this.hasParking = hasParking;
    }

    public boolean getHasHeating() {
        return hasHeating;
    }

    public void setHasHeating(boolean hasHeating) {
        this.hasHeating = hasHeating;
    }

    public int getStageFloor() {
        return stageFloor;
    }

    public void setStageFloor(int stageFloor) {
        this.stageFloor = stageFloor;
    }

    public RentTypes getRentType() {
        return rentType;
    }

    public void setRentType(RentTypes rentType) {
        this.rentType = rentType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    public StatusTypes getStatus() {
        return status;
    }

    public void setStatus(StatusTypes status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ApartmentModel{" +
                "unitNumber=" + unitNumber +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", location=" + location +
                ", size=" + size +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", hasParking=" + hasParking +
                ", hasHeating=" + hasHeating +
                ", stageFloor=" + stageFloor +
                ", rentType=" + rentType +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", status=" + status +
                ", images=" + images +
                ", messages=" + messages +
                '}';
    }
}