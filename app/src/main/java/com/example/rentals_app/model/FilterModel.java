package com.example.rentals_app.model;

import java.io.Serializable;

public class FilterModel implements Serializable {
    public String filterTitle;
    public int filterRooms;
    public int filterBathRooms;
    public int filterParking;
    public int filterHeating;

    public FilterModel() {
    }

    public FilterModel(String filterTitle, int filterRooms, int filterBathRooms, int filterParking, int filterHeating) {
        this.filterTitle = filterTitle;
        this.filterRooms = filterRooms;
        this.filterBathRooms = filterBathRooms;
        this.filterParking = filterParking;
        this.filterHeating = filterHeating;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public int getFilterRooms() {
        return filterRooms;
    }

    public void setFilterRooms(int filterRooms) {
        this.filterRooms = filterRooms;
    }

    public int getFilterBathRooms() {
        return filterBathRooms;
    }

    public void setFilterBathRooms(int filterBathRooms) {
        this.filterBathRooms = filterBathRooms;
    }

    public int isFilterParking() {
        return filterParking;
    }

    public void setFilterParking(int filterParking) {
        this.filterParking = filterParking;
    }

    public int isFilterHeating() {
        return filterHeating;
    }

    public void setFilterHeating(int filterHeating) {
        this.filterHeating = filterHeating;
    }
}
