package com.example.mobilemonopoly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;



public class StationLocationInfo extends AppCompatActivity{

    private ImageView stationImageView;
    private TextView stationInfoTextView;
    private Button backButton, prevButton, nextButton;
    private int stationIndex;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_location);

        stationImageView = findViewById(R.id.stationLocationImage);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        stationInfoTextView = findViewById(R.id.stationDescriptions);
        backButton = findViewById(R.id.backButton);


        backButton.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        stationIndex = intent.getIntExtra("stationIndex", 0); // 獲取索引
        // stationInfoTextView.setText(stationInfo);

    }
}
