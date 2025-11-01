package com.example.mobilemonopoly.data.model;

import android.provider.CloudMediaProvider;

public class StationSpot {

    private int stationID;
    private String place;
    private String description;
    private String[] pictures;
    private double locationX;
    private double locationY;

    public StationSpot(int stationID, String place, String description, String[] pictures, double locationX, double locationY) {
        this.stationID = stationID;
        this.place = place;
        this.description = description;
        this.pictures = pictures;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public int getStationID() {
        return stationID;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPictures() {
        return pictures;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }
}
