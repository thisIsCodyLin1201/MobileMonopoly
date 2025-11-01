/* 0213 新增 by 孟*/
package com.example.mobilemonopoly.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_images")
public class GameImage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "URL")
    private String imagePath; // 圖片的本地路徑或 URL
    @ColumnInfo(name = "description")
    private String description; // 圖片的簡介
    @ColumnInfo(name = "position")
    private int position; // 這張圖片對應的棋盤格子位置

    public GameImage(String name,String imagePath, String description, int position) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.position = position;
    }

    // Getters and Setters
    public String name() { return name; }
    public void name(String name) { this.name = name; }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}