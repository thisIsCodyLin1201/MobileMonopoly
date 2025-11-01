package com.example.mobilemonopoly.data.model;

public class Station {

    private int stationID;
    private String image;
    private int finish;

    public Station(int stationID, String image, int finish) {
        this.stationID = stationID;
        this.image = image;
        this.finish = finish;
    }

    public int getStationID() {
        return stationID;
    }

    public String getImage() {
        return image;
    }

    public int getFinish() {
        return finish;
    }

}
