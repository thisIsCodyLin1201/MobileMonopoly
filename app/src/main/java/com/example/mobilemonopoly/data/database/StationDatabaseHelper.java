package com.example.mobilemonopoly.data.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobilemonopoly.data.model.MultipleChoiceQuestion;
import com.example.mobilemonopoly.data.model.Station;
import com.example.mobilemonopoly.data.model.StationSpot;
import com.example.mobilemonopoly.data.model.TrueFalseQuetion;

import java.util.ArrayList;
import java.util.List;

public class StationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stations.db";
    private static final int DATABASE_VERSION = 1;

    public StationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table structure here if needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if necessary
    }

    public List<StationSpot> getAllStationSpots() {
        List<StationSpot> stationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("StationSpot", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int stationID = cursor.getInt(cursor.getColumnIndexOrThrow("stationID"));
                String place = cursor.getString(cursor.getColumnIndexOrThrow("place"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                String[] pictures = new String[]{
                        cursor.getString(cursor.getColumnIndexOrThrow("picture1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("picture2")),
                        cursor.getString(cursor.getColumnIndexOrThrow("picture3")),
                        cursor.getString(cursor.getColumnIndexOrThrow("picture4"))
                };

                double locationX = cursor.getDouble(cursor.getColumnIndexOrThrow("locationX"));
                double locationY = cursor.getDouble(cursor.getColumnIndexOrThrow("locationY"));

                stationList.add(new StationSpot(stationID, place, description, pictures, locationX, locationY));
            }
            cursor.close();
        }

        return stationList;
    }

    public List<Station> getAllStations() {
        List<Station> stationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Station", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int stationID = cursor.getInt(cursor.getColumnIndex("stationID"));
                @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));
                @SuppressLint("Range") int finish = cursor.getInt(cursor.getColumnIndex("finish"));

                stationList.add(new Station(stationID, image, finish));
            }
            cursor.close();
        }

        return stationList;
    }

    public List<TrueFalseQuetion> getAllTFQuestions() {
        List<TrueFalseQuetion> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TrueFalseQuestion", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("tfQuestionID"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("tfQuestion"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("tfAnswer"));

                questionList.add(new TrueFalseQuetion(id, question, answer));
            }
            cursor.close();
        }

        return questionList;
    }

    public List<MultipleChoiceQuestion> getAllMCQuestions() {
        List<MultipleChoiceQuestion> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("MultipleChoiceQuestion", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("mcQuestionID"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("mcQuestion"));
                String choice1 = cursor.getString(cursor.getColumnIndexOrThrow("choice1"));
                String choice2 = cursor.getString(cursor.getColumnIndexOrThrow("choice2"));
                String choice3 = cursor.getString(cursor.getColumnIndexOrThrow("choice3"));
                String choice4 = cursor.getString(cursor.getColumnIndexOrThrow("choice4"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("mcAnswer"));

                questionList.add(new MultipleChoiceQuestion(id, question, choice1, choice2, choice3, choice4, answer));
            }
            cursor.close();
        }

        return questionList;
    }

}
