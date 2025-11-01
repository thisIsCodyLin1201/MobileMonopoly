package com.example.mobilemonopoly.data.model;

public class MCQuestionChoice {

    private int stationID;
    private int mcQuestionID;

    public MCQuestionChoice(int stationID, int mcQuestionID){
        this.stationID = stationID;
        this.mcQuestionID = mcQuestionID;
    }

    public int getStationID() {
        return stationID;
    }

}
