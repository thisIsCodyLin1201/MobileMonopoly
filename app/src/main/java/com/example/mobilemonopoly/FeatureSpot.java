package com.example.mobilemonopoly;

import java.io.Serializable;

public class FeatureSpot  implements Serializable {
    public float x;
    public float y;
    public int index;
    public int imageResId;
    public String title;
    public String description;


    public FeatureSpot(float x, float y, int index, int imageResId, String title,String description) {
        this.x = x;
        this.y = y;
        this.index = index;
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
    }
}
