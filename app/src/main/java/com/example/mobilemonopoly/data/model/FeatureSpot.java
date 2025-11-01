package com.example.mobilemonopoly.data.model;

public class FeatureSpot {

    private int featureSpotID;
    private int stationSpotID;
    private int x;
    private int y;
    private int index;
    private String spotImage;
    private String title;
    private String detailDescription;

    public FeatureSpot(int featureSpotID, int stationSpotID, int x, int y, int index, String spotImage, String title, String detailDescription){
        this.featureSpotID = featureSpotID;
        this.stationSpotID = stationSpotID;
        this.x = x;
        this.y = y;
        this.index = index;
        this.spotImage = spotImage;
        this.title = title;
        this.detailDescription = detailDescription;
    }

    public int getFeatureSpotID() {
        return featureSpotID;
    }

    public int getStationSpotID() {
        return stationSpotID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public String getSpotImage() {
        return spotImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

}
