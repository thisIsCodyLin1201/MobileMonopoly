package com.example.mobilemonopoly;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class StationDetailActivity extends AppCompatActivity {

    private ImageView stationImageView;
    private TextView stationInfoTextView;
    private Button backButton, checkInButton, learnMoreButton, prevButton, nextButton;
    private ImageButton stationLocationButton;
    private LocationManager locationManager;
    private ProgressBar progressBar;
    private int currentImageIndex = 0; // 當前顯示的圖片索引
    private int[][] stationImages; // 用來儲存站點的多張圖片
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private boolean hasSeenTutorial = false;


//    private static final double[][] stationLocations = {
//            {24.988270, 121.618095}, // 站點 1 1792茶園
//            {24.998224, 121.612849}, // 站點 2 隱修院
//            {24, 121}, // 站點 3 筊杯
//            {24.992187, 121.619625}, // 站點 4 向天湖
//            {24.984065, 121.614538}, // 站點 5 驪山老母-無極慈母宮
//            {24, 121}, // 站點 6 抽籤
//            {37.422015, -122.084063}, // 站點 5 阿柔洋產業道路（虛擬機測試座標）
//            {24.975459, 121.614972}, // 站點 7 青山香草教育農園
//            {24, 121}, // 站點 9 筊杯
//            {24.993184, 121.617922}, // 站點 10 阿柔親水空間
//            {24.993897, 121.614710}, // 站點 11 石媽祖古道
//            {24, 121}, // 站點 12 抽籤
//            {24.996291, 121.613600}, // 站點 14 大崎嶺步道
//            {24.988270, 121.618095}, // 站點 13 金城茶園
//            {24.975210, 121.615131}, // 站點 15 天南宮
//            {24.973893, 121.614283}, // 站點 16 猴山岳步道雙扇蕨
//            {24.973378, 121.614516}, // 站點 17 深坑木柵交界
//            {24.998224, 121.612849} // 站點 08 106 club house
////            {24.986753, 121.576535} //政大商願
//    };
    private static final double[][] stationLocations = {
        {37.422015, -122.084063}, //全部用虛擬機測試座標
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063},
        {37.422015, -122.084063}
    };



    private int stationIndex; // 用於接收站點的索引，判斷對應 GPS 位置

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        //把android手機下方內建的導覽bar隱藏
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        stationImageView = findViewById(R.id.stationImage);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        stationInfoTextView = findViewById(R.id.stationDescriptions);
        backButton = findViewById(R.id.backButton);
        checkInButton = findViewById(R.id.checkInButton);
        learnMoreButton = findViewById(R.id.learnMoreButton);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        progressBar = findViewById(R.id.progressBar);
        stationLocationButton = findViewById(R.id.stationLocationButton);


        SharedPreferences prefs = getSharedPreferences("station_detail_prefs", MODE_PRIVATE);
        boolean hasSeenTapTarget = prefs.getBoolean("has_seen_station_tutorial", false);
        if (!hasSeenTapTarget) {
            showTapTargetView();// 首次顯示提示導覽
            prefs.edit().putBoolean("has_seen_station_tutorial", true).apply();// 設定已經看過
        }

        Intent intent = getIntent();
        stationImages = (int[][]) intent.getSerializableExtra("stationImage");  // 接收圖片資源的二維陣列
        stationIndex = intent.getIntExtra("stationIndex", 0);  // 接收站點索引
        String stationInfo = intent.getStringExtra("stationDescription");
        stationIndex = intent.getIntExtra("stationIndex", -1); // 預設為 -1 以避免錯誤

        if (stationIndex >= 0 && stationIndex < stationImages.length) {
            // 顯示站點對應的第一張圖片
            stationImageView.setImageResource(stationImages[stationIndex][currentImageIndex]);
        }

        if (stationIndex < 0 || stationIndex >= stationLocations.length) {
            Toast.makeText(this, "❌ 無效的站點索引！", Toast.LENGTH_SHORT).show();
            finish(); // 結束 Activity，避免程式崩潰
            return;
        }

        // 顯示對應站點的圖片與資訊
        // 點擊上一張圖片
        prevButton.setOnClickListener(v -> {
            if (currentImageIndex > 0) {
                currentImageIndex--;
                stationImageView.setImageResource(stationImages[stationIndex][currentImageIndex]);
            } else {
                Toast.makeText(this, "已經是第一張圖片了", Toast.LENGTH_SHORT).show();
            }
//            stationImageView.setImageResource(stationImages[stationIndex][currentImageIndex]);
        });
        // 點擊下一張圖片
        nextButton.setOnClickListener(v -> {
            if (currentImageIndex < stationImages[stationIndex].length - 1) {
                currentImageIndex++;
                stationImageView.setImageResource(stationImages[stationIndex][currentImageIndex]);
            } else {
                Toast.makeText(this, "已經是最後一張圖片了", Toast.LENGTH_SHORT).show();
            }
//            stationImageView.setImageResource(stationImages[stationIndex][currentImageIndex]);
        });
        stationInfoTextView.setText(stationInfo);

        backButton.setOnClickListener(v -> {
            finish();
        });

        // 打卡按鈕
        checkInButton.setOnClickListener(v -> {
            // 檢查 GPS 權限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getCurrentLocation();
            }
        });

        // LearnMore按鈕
        learnMoreButton.setOnClickListener(v -> {
            Intent learnMoreIntent = new Intent(StationDetailActivity.this, LearnMore.class);
            learnMoreIntent.putExtra("stationIndex", stationIndex);  // 傳遞索引
            learnMoreIntent.putExtra("stationImage", stationImages);  // 傳遞圖片二維陣列
            startActivity(learnMoreIntent);
        });

        // stationLocation按鈕
        stationLocationButton.setOnClickListener(v -> {
            Toast.makeText(StationDetailActivity.this,
                    "功能開發中！",
                    Toast.LENGTH_LONG).show();

//            Intent stationLocationIntent = new Intent(StationDetailActivity.this, StationLocationInfo.class);
//            stationLocationIntent.putExtra("stationIndex", stationIndex);  // 傳遞索引
//            startActivity(stationLocationIntent);
        });

    }

    //add by Jessy
    private void showTapTargetView() {
        TapTargetView.showFor(this,
                TapTarget.forView(checkInButton, "\uD83D\uDCCC打卡功能", "點擊這裡來完成打卡！\n只要距離站點3公里內就算打卡成功\n完成10個站點可以獲得深坑小禮物！")
                        .outerCircleColor(R.color.orange) //外圍顏色
                        .targetCircleColor(R.color.white) //內圈重點顏色
                        .titleTextSize(20) //標題文字大小
                        .titleTextColor(R.color.black) //標題文字顏色
                        .descriptionTextSize(15) //說明文字大小
                        .descriptionTextColor(R.color.black) //說明文字顏色
                        .dimColor(R.color.black) // 背景暗化顏色
                        .cancelable(true) //點擊其他範圍取消
                        .tintTarget(false) //把按鈕加顏色
                        .targetRadius(50), //目標圓圈半徑
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }
                    @Override
                    public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                        super.onTargetDismissed(view, userInitiated);
                        showSecondTapTargetView();
                    }
                });

    }

    private void showSecondTapTargetView() {
        TapTargetView.showFor(this,
                TapTarget.forView(learnMoreButton, "\uD83D\uDCD6瞭解更多", "快來看更多有關於深坑的故事！")
                        .outerCircleColor(R.color.orange)
                        .targetCircleColor(R.color.white)
                        .titleTextSize(20)
                        .titleTextColor(R.color.black)
                        .descriptionTextSize(15)
                        .descriptionTextColor(R.color.black)
                        .dimColor(R.color.black)
                        .cancelable(true)
                        .tintTarget(false)
                        .targetRadius(50),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }
                });
    }

//    //add by Jessy
//    // 檢查是否第一次顯示引導
//    private boolean isFirstTime() {
//        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
////      boolean hasSeenTutorial = prefs.getBoolean("has_seen_tutorial", false);
//        Log.e("has_seen_tutorial value", String.valueOf(hasSeenTutorial));
//        return !hasSeenTutorial; // 如果返回 false，則表示第一次顯示
//    }
//
//    //add by Jessy
//    // 記錄用戶已經看過引導
//    private void setHasSeenTutorial() {
//        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
//        prefs.edit().putBoolean("has_seen_tutorial", true).apply(); // 設置為 true，表示用戶已經看過引導
//    }

    // 獲取當前位置
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        try{
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {


                    double userLat = location.getLatitude();
                    double userLng = location.getLongitude();

                    // 計算與目標站點的距離
                    double targetLat = stationLocations[stationIndex][0];
                    double targetLng = stationLocations[stationIndex][1];
                    float[] results = new float[1];
                    Location.distanceBetween(userLat, userLng, targetLat, targetLng, results);
                    float distance = results[0];

                    int nextStationIndex = (stationIndex + 1) % stationLocations.length;
                    double nextTargetLat = stationLocations[nextStationIndex][0];
                    double nextTargetLng = stationLocations[nextStationIndex][1];
                    float[] nextResults = new float[1];
                    Location.distanceBetween(userLat, userLng, nextTargetLat, nextTargetLng, nextResults);
                    float nextDistance = nextResults[0];
                    progressBar.setVisibility(View.GONE);

                    // 座標在其他站點也可以讓阿柔洋產業道路打卡
                    boolean isNearAnyStation = false;
                    for (int i = 0; i < stationLocations.length; i++) {
                        double lat = stationLocations[i][0];
                        double lng = stationLocations[i][1];
                        float[] result = new float[1];
                        Location.distanceBetween(userLat, userLng, lat, lng, result);
                        if (result[0] <= 3000) {
                            isNearAnyStation = true;
                            break;
                        }
                    }

                    // 打卡成功/失敗判斷
                    if ((stationIndex == 6 && isNearAnyStation) || distance <= 3000) {
                        markStationAsCheckedIn(stationIndex);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("stationIndex", stationIndex);
                        setResult(RESULT_OK, resultIntent);

                        if (stationIndex == stationLocations.length - 1) {
                            // 如果是最後一站，不顯示距離下一站點距離
                            Toast.makeText(StationDetailActivity.this,
                                    "打卡成功！",
                                    Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(StationDetailActivity.this, "打卡成功！距離下一站點 " + Math.round(nextDistance/1000) + "公里", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(StationDetailActivity.this, "打卡失敗，距離站點 " + Math.round(distance/1000) + "公里", Toast.LENGTH_LONG).show();
                    }
                }
            }, null);
        } catch (Exception e) {
            Toast.makeText(this, "❌ 位置請求失敗", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

    }



    // 用set紀錄已經打卡成功的站點
    private void markStationAsCheckedIn(int stationIndex) {
        SharedPreferences prefs = getSharedPreferences("checkin_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> checkedInStations = prefs.getStringSet("checked_in", new HashSet<>());
        checkedInStations.add(String.valueOf(stationIndex));
        editor.putStringSet("checked_in", checkedInStations);
        editor.apply();
    }

    // 處理權限請求結果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "❌ 需要 GPS 權限才能進行打卡", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
