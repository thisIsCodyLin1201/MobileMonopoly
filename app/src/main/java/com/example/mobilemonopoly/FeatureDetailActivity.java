package com.example.mobilemonopoly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FeatureDetailActivity extends AppCompatActivity {

    private Button backButton, prevButton, nextButton;
    private int currentSpotIndex;
    private ArrayList<FeatureSpot> spotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_detail);

        // 隱藏導航欄
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        ImageView imageView = findViewById(R.id.featureImage);
        TextView titleView = findViewById(R.id.featureTitle);
        TextView textView = findViewById(R.id.featureText);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        // 從 Intent 中取得資料
        Intent intent = getIntent();
        currentSpotIndex = intent.getIntExtra("spotIndex", 0);
        spotList = (ArrayList<FeatureSpot>) intent.getSerializableExtra("spotList");

        // 顯示收到的 spotIndex
        Log.d("FeatureDetailActivity", "Received Spot Index: " + currentSpotIndex); // 在這裡檢查接收到的 spotIndex


        if (spotList != null) {
            // 初始化顯示的資料
            updateContent(spotList.get(currentSpotIndex));
        }

        // 顯示對應站點的圖片與資訊
        // 點擊上一張圖片
        prevButton.setOnClickListener(v -> {
            if (currentSpotIndex > 0) {
                currentSpotIndex--;
//                updateContent(spotList.get(currentSpotIndex));
            } else {
                currentSpotIndex=2;
//                Toast.makeText(this, "已經是第一張圖片了", Toast.LENGTH_SHORT).show();
            }
            updateContent(spotList.get(currentSpotIndex));
        });

        // 點擊下一張圖片
        nextButton.setOnClickListener(v -> {
            if (currentSpotIndex < 2) {
                currentSpotIndex++;
//                updateContent(spotList.get(currentSpotIndex));
            } else {
                currentSpotIndex=0;
//                Toast.makeText(this, "已經是最後一張圖片了", Toast.LENGTH_SHORT).show();
            }
            updateContent(spotList.get(currentSpotIndex));
        });
    }

    // 更新顯示內容
    private void updateContent(FeatureSpot spot) {
        ImageView imageView = findViewById(R.id.featureImage);
        TextView titleView = findViewById(R.id.featureTitle);
        TextView textView = findViewById(R.id.featureText);

        // 更新圖片
        imageView.setImageResource(spot.imageResId);

        // 更新標題
        titleView.setText(spot.title);

        // 更新文字描述
        textView.setText(spot.description);
    }
}
