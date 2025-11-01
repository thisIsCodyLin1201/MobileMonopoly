package com.example.mobilemonopoly.data.model;

public class TFQuestionChoice {

    private int stationID;
    private int tfQuestionID;

    public TFQuestionChoice(int stationID, int tfQuestionID){
        this.stationID = stationID;
        this.tfQuestionID = tfQuestionID;
    }

    public int getStationID() {
        return stationID;
    }

    public int getTfQuestionID() {
        return tfQuestionID;
    }

}
