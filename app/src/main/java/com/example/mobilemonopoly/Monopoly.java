package com.example.mobilemonopoly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Monopoly extends AppCompatActivity {
    private static final int CELL_SIZE_DP = 80; // 格子大小
    private static final int CELL_MARGIN_DP = 2; // 格子間距

    // 定義每個位置的圖片資源列表
    private final int[] images = {
            R.drawable.black1, R.drawable.black2, R.drawable.black3, R.drawable.black4, R.drawable.black5,  // Top row
            R.drawable.black6, R.drawable.black7, R.drawable.black8, R.drawable.black9, R.drawable.black10, R.drawable.black1, R.drawable.black7, // Right column
            R.drawable.black11, R.drawable.black12, R.drawable.black13, R.drawable.black14, R.drawable.black15,  // Bottom row
            R.drawable.bird, R.drawable.fish, R.drawable.ghostflower, R.drawable.gingerflower, R.drawable.bug, R.drawable.black7, R.drawable.black1  // Left column
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monopoly);

        // 初始化容器
        LinearLayout topContainer = findViewById(R.id.top_container);
        LinearLayout bottomContainer = findViewById(R.id.bottom_container);
        LinearLayout leftContainer = findViewById(R.id.left_container);
        LinearLayout rightContainer = findViewById(R.id.right_container);

        // 創建格子
        createCells(topContainer, 5, true, 0);       // Top row: 0-4
        createCells(rightContainer, 7, false, 5);    // Right column: 5-11
        createCells(bottomContainer, 5, true, 12);   // Bottom row: 12-16
        createCells(leftContainer, 7, false, 17);    // Left column: 17-23
    }

//    private void createCells(LinearLayout container, int count, boolean isHorizontal, int startIndex) {
//        // 將 dp 轉換為像素單位
//        int cellSize = (int) (CELL_SIZE_DP * getResources().getDisplayMetrics().density);
//        int overlapMargin = -2; // 設置負邊距來讓圖片之間的邊緣重疊，消除接縫
//        int adjustedSize = cellSize + Math.abs(overlapMargin); // 調整大小以適配重疊效果
//
//        // 設置容器的寬度和高度
//        if (isHorizontal) {
//            // 水平容器（上下排）
//            int totalWidth = (adjustedSize * count);
//            container.getLayoutParams().width = totalWidth;
//        } else {
//            // 垂直容器（左右排）
//            int totalHeight = (adjustedSize * count);
//            container.getLayoutParams().height = totalHeight;
//        }
//
//        for (int i = 0; i < count; i++) {
//            ImageView cell = new ImageView(this);
//
//            // 設置格子的大小和邊距
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(adjustedSize, adjustedSize);
//            if (i > 0) { // 除了第一個格子，其他格子需要設置負邊距
//                if (isHorizontal) {
//                    params.leftMargin = overlapMargin;
//                } else {
//                    params.topMargin = overlapMargin;
//                }
//            }
//            cell.setLayoutParams(params);
//
//            // 設置圖片
//            int imageIndex = startIndex + i;
//            if (imageIndex < images.length) {
//                cell.setImageResource(images[imageIndex]);
//            }
//
//            // 設置縮放類型
//            cell.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            // 添加點擊事件
//            cell.setOnClickListener(v -> {
//                Intent intent = new Intent(NewImageLayoutActivity.this, MainActivity.class);
//                startActivity(intent);
//            });
//
//            container.addView(cell);
//        }
//
//        // 更新容器的布局參數
//        container.requestLayout();
//    }


    private void createCells(LinearLayout container, int count, boolean isHorizontal, int startIndex) {
        int cellSize = (int) (CELL_SIZE_DP * getResources().getDisplayMetrics().density);
        int margin = (int) (CELL_MARGIN_DP * getResources().getDisplayMetrics().density);

        // 設置容器的寬度和高度
        if (isHorizontal) {
            // 水平容器（上下排）
            int totalWidth = (cellSize * count) + (margin * (count - 1));
            container.getLayoutParams().width = totalWidth;
        } else {
            // 垂直容器（左右排）
            int totalHeight = (cellSize * count) + (margin * (count - 1));
            container.getLayoutParams().height = totalHeight;
        }

        for (int i = 0; i < count; i++) {
            ImageView cell = new ImageView(this);

            // 設置格子的大小和邊距
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellSize, cellSize);
            if (i > 0) {  // 除了第一個格子，其他都加上間距
                if (isHorizontal) {
                    params.leftMargin = margin;
                } else {
                    params.topMargin = margin;
                }
            }
            cell.setLayoutParams(params);

            // 設置圖片
            int imageIndex = startIndex + i;
            if (imageIndex < images.length) {
                cell.setImageResource(images[imageIndex]);
            }

            // 設置縮放類型
            cell.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 添加點擊事件
            cell.setOnClickListener(v -> {
                Intent intent = new Intent(Monopoly.this, MainActivity.class);
                startActivity(intent);
            });

            container.addView(cell);
        }

        // 更新容器的布局參數
        container.requestLayout();
    }
}
