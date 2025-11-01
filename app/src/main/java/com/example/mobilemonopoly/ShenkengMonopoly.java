package com.example.mobilemonopoly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.example.mobilemonopoly.data.database.AppDatabase;

import com.example.mobilemonopoly.data.database.StationDatabaseHelper;
import com.example.mobilemonopoly.data.model.GameImage;
import com.example.mobilemonopoly.data.model.MultipleChoiceQuestion;
import com.example.mobilemonopoly.data.model.Station;
import com.example.mobilemonopoly.data.model.StationSpot;
import com.example.mobilemonopoly.data.model.TrueFalseQuetion;
import com.example.mobilemonopoly.ui.viewmodel.GameImageViewModel;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;


public class ShenkengMonopoly extends AppCompatActivity {

    private ImageView mainImage; // 中間顯示的圖片
    //<伍註解掉3/21>private Button photoButton; // 拍照的按鈕
    private Button checkButton;
    private ConstraintLayout rootLayout; // 監聽整個畫面 為了讓點擊其他部分時隱藏拍照按鈕和相片
    private ImageView answerFeedbackImage;
    private ImageView stationImageView;
    private TextView aiDialog;
    private Button optionA, optionB, optionC, optionD;
    private Button trueButton, falseButton;
    private ImageView background;
    private String problem;
    private ImageButton stationButton;
    private int currentStationIndex = -1;
    private StationDatabaseHelper dbHelper;
    private List<StationSpot> stationSpotList;
    private List<Station> stationList;
    private String[] stationDescriptions;
    private List<TrueFalseQuetion> tfQuestionList;
    private List<MultipleChoiceQuestion> mcQuestionList;


//    private String[][] multipleChoiceQuestions = {
//            {"阿柔洋產業步道在深坑的哪一邊？", "東邊", "西邊", "南邊", "北邊", "C"},
//            {"阿柔洋產業步道兩旁種的茶葉是哪種茶？", "包種茶", "紅茶", "東方美人茶", "綠茶", "A"}
//    };
    // 已串資料庫
    private String[][] multipleChoiceQuestions;
    private String[][] trueFalseQuestions;
//    private String[][] trueFalseQuestions = {
//            {"1792年是文獻最早紀載在深坑種植茶葉的年份？", "是", "不是", "A"},
//            {"驪山老母供俸的是樊梨花？", "是", "不是", "B"},
//            {"向天湖是一座湖？", "是", "不是", "B"}
//    };

    // 已串資料庫
//    private String[] stationDescriptions = {
//            "金城茶園\n\n黃金城老師的種茶動機，起源於自己喝到很好喝的茶，就將家中的竹筍園改成茶園，取名「金城茶園」，堅持友善無毒栽種金萱、青心烏龍等。",
//            "大崎嶺步道口\n\n大崎嶺步道入口位在阿柔洋產業道路約250公尺，步道入口在道路右側槡園旁的一條小徑。昔日為聯繫聚落的重要山徑。步道沿途林木蒼鬱，景色優美，並保留許多歷史遺跡，如石階與古碑，展現先民開拓的足跡。",
//            "筊杯",
//            "石媽祖古道\n\n石媽祖步道入口在阿柔洋產業道路約450公尺處，步道終點為鎮南宮石媽祖廟，沿路兩側桂花樹撲鼻，風景優美，適合親子健行。",
//            "阿柔坑溪親水空間\n\n阿柔坑溪隱身於青翠山林間，溪水沿層層石階流瀉，形成如畫般的階梯水景。溪畔綠意盎然，並設有涼亭供遊客休憩，環境幽靜清新。漫步於潺潺水聲中，遠離城市喧囂，感受大自然的寧靜與療癒氣息，是放鬆身心的理想去處。",
//            "向天湖\n\n位於阿柔洋產業道路東側，仍保有原始田園風貌，種植水稻、蔬菜與綠竹筍，四季更迭展現農村之美。每年四月，螢火蟲飛舞，螢光閃閃。房舍後方有大片桂竹林，環境純樸自然。插秧與收割時，鄰里相互幫忙，充滿濃厚人情味。",
//            "筊杯",
//            "加爾默羅聖母聖衣隱修院\n\n位在寧靜的深山中的加爾默羅聖母聖衣隱修院，為天主教最嚴格的隱修院之一。在靜默中祈禱，隱修女度著靜默生活，終身奉獻，祈禱刻苦、也透過彼此相互祈禱，使她們之間的生命更加緊密地連結在一起。",
//            "1792茶園\n\n1792茶園創辦人黃土水深刻體會到深坑茶產業面臨的挑戰，決心以實際行動振興在地茶業。2024年新北好茶石碇冬季文山包種茶比賽，榮獲特等獎！為振興深坑茶產業奠定了關鍵基石。",
//            "驪山老母-無極慈母宮\n\n創建於民國九十年，主祀驪山老母，奉祀太上道祖。驪山老母為道教女仙，常指點迷津、傳授秘籍，深受信眾崇敬，民間影響深遠。蔡貴德宮主因夢中多次感應，依神示雕塑聖像，並擇吉日開光奉祀。",
//            "抽籤",
//            "阿柔洋產業道路\n\n阿柔洋產業道路四季皆有不同風貌，春櫻綻放、夏日翠綠、秋季迷人、冬晨雲霧繚繞，變化萬千，景色迷人。這條道路是單車愛好者必訪之地，以其綿延的緩升坡聞名，挑戰體力之餘，也能欣賞沿途壯闊山景與純樸田野風光。",
//            "青山香草教育農園\n\n阿柔地產的桑麻丸與在地花藝，採用在地生產的芝麻、蜂蜜和桑葉製作而成，展現深坑滿滿的農藝魅力與文化底蘊。",
//            "抽籤",
//            "天南宮\n\n敬奉的三太子元祖已有三百多年歷史，於清嘉慶年間自安溪縣遷祀來台，日據時期供奉於文山郡深坑庄大崙尾一號。每年農曆九月九日為三太子聖誕，信眾齊聚慶典，共同祈福，延續傳統信仰。",
//            "106 Club House\n\n阿柔茶文化步道的起點，位於台106乙線的鐵馬驛站，距離台北101開車只要10分鐘。鄰近的阿柔楊產業道路則是自行車好手練車的地方。",
//            "新北台北縣市交會處：深坑與木柵\n\n此交會處為行政區交界，一邊為新北市深坑區、另一邊為台北市文山區。"
//    };
//    String htmlText = "<div style='text-align:center;'>1792茶園</div><br><br>1792茶園創辦人黃土水深刻體會到...";



    // 站點按鈕 ID list
    private int[] buttonIds = {
            R.id.button_station_1,
            R.id.button_station_2,
            R.id.button_station_3,
            R.id.button_station_4,
            R.id.button_station_5,
            R.id.button_station_6,
            R.id.button_station_7,
            R.id.button_station_8,
            R.id.button_station_9,
            R.id.button_station_10,
            R.id.button_station_11,
            R.id.button_station_12,
            R.id.button_station_13,
            R.id.button_station_14,
            R.id.button_station_15,
            R.id.button_station_16,
            R.id.button_station_17,
            R.id.button_station_18
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shenkeng_monopoly);

        dbHelper = new StationDatabaseHelper(this);
        // 從資料庫中獲取所有站點資訊
        stationList = dbHelper.getAllStations();
        stationSpotList = dbHelper.getAllStationSpots();
        tfQuestionList = dbHelper.getAllTFQuestions();
        mcQuestionList = dbHelper.getAllMCQuestions();
        copyDatabaseIfNeeded(this); // by 林 0528

        // 問答題目已串資料庫
        trueFalseQuestions = new String[tfQuestionList.size()][4];
        for (int i = 0; i < tfQuestionList.size(); i++) {
            TrueFalseQuetion q = tfQuestionList.get(i);
            trueFalseQuestions[i][0] = q.getTfQuestion();         // 題目
            trueFalseQuestions[i][1] = "是";                       // 固定選項
            trueFalseQuestions[i][2] = "不是";                     // 固定選項
            trueFalseQuestions[i][3] = q.getTfAnswer().equals("是") ? "A" : "B"; // 正確答案轉 A or B
        }
        multipleChoiceQuestions = new String[mcQuestionList.size()][6]; // 6欄：題目+4選項+正解
        for (int i = 0; i < mcQuestionList.size(); i++) {
            MultipleChoiceQuestion q = mcQuestionList.get(i);
            multipleChoiceQuestions[i][0] = q.getMcQuestion(); // 題目
            multipleChoiceQuestions[i][1] = q.getChoice1();     // A
            multipleChoiceQuestions[i][2] = q.getChoice2();     // B
            multipleChoiceQuestions[i][3] = q.getChoice3();     // C
            multipleChoiceQuestions[i][4] = q.getChoice4();     // D
            multipleChoiceQuestions[i][5] = q.getMcAnswer();    // 正解（"A"/"B"/"C"/"D"）
        }

        // 文字敘述已串資料庫
        stationDescriptions = new String[17];
        int spotIndex = 0;
        for (int i = 0; i < 17; i++) {
            if(i==2 || i==6 || i==10 || i==13){
                continue;
            }else{
                StationSpot spot = stationSpotList.get(spotIndex);
                stationDescriptions[i] = spot.getPlace() + "\n\n" + spot.getDescription();
                spotIndex++;
            }
        }

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


        resetAllCheckIns(); // 重置引導和打卡紀錄，註解掉即不重置!!!!!


        mainImage = findViewById(R.id.mainImage);
        checkButton = findViewById(R.id.checkButton);
        rootLayout = findViewById(R.id.rootLayout);
        answerFeedbackImage = findViewById(R.id.answerFeedbackImage);

        aiDialog = findViewById(R.id.aiDialog);
//        stationImageView = findViewById(R.id.stationImageView);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        background = findViewById(R.id.fixedBackground);

        SharedPreferences prefs = getSharedPreferences("app_preferences", MODE_PRIVATE);
        boolean hasSeenTapTarget = prefs.getBoolean("has_seen_tap_target", false);
        if (!hasSeenTapTarget) {
            showTapTargetView();// 首次顯示提示導覽
            prefs.edit().putBoolean("has_seen_tap_target", true).apply();// 設定已經看過
        }

        // 站點對應的圖片(尚未串DB)
        int[][] stationImages = {
                {R.drawable.original_14_1, R.drawable.original_14_2, R.drawable.original_14_3}, // 金城茶園
                {R.drawable.original_15, R.drawable.original_15_1, R.drawable.original_15_3}, // 大旗領步道口
                {R.drawable.aipicture_4}, // 筊杯
                {R.drawable.original_12, R.drawable.original_12_1, R.drawable.original_12_2}, // 石媽祖
                {R.drawable.original_11, R.drawable.original_11_1, R.drawable.original_11_2, R.drawable.original_11_3, R.drawable.original_11_4}, // 親水公園
                {R.drawable.original_5, R.drawable.original_5_1, R.drawable.original_5_3, R.drawable.original_5_4}, // 向天湖
                {R.drawable.aipicture_11}, // 筊杯
                {R.drawable.original_3_1, R.drawable.original_3}, // 隱修院
                {R.drawable.original_2, R.drawable.original_2_1}, // 1792茶園
                {R.drawable.original_1, R.drawable.original_1_2, R.drawable.original_1_3}, // 離山老母
                {R.drawable.original_7}, // 抽籤
                {R.drawable.original_6, R.drawable.aipicture_6}, // 阿柔洋產業道路
                {R.drawable.original_8_1, R.drawable.original_8_2, R.drawable.original_8, R.drawable.original_8_4}, // 青山香草
                {R.drawable.original_13}, // 抽籤
                {R.drawable.original_16, R.drawable.original_16_1}, // 天南宮
                {R.drawable.original_20, R.drawable.original_20_1},  // 106 club house
                {R.drawable.original_18_2}  // 深坑木柵交界處
        };

        // 設定所有站點按鈕的點擊事件
        for (int i = 0; i < buttonIds.length; i++) {
            int index = i;
            ImageButton button = findViewById(buttonIds[i]);

            // 設定圖片（已串資料庫）
            if (stationList != null && index < stationList.size()) {
                Station station = stationList.get(index);
                String imageName = station.getImage();
                int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());

                if (imageResId != 0) {
                    button.setImageResource(imageResId);
                } else {
                    button.setImageResource(R.drawable.aipicture_22); // 預設圖
                }
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index == 2 || index == 6) {
                        currentStationIndex = index;
                        problem = "筊杯";
                        mainImage.setVisibility(View.GONE);
                        checkButton.setVisibility(View.GONE);
                        background.setVisibility(View.GONE);
                        showRandomQuestion();
                    } else if (index == 10 || index == 13) { // 抽籤
                        currentStationIndex = index;
                        problem = "抽籤";
                        mainImage.setVisibility(View.GONE);
                        checkButton.setVisibility(View.GONE);
                        background.setVisibility(View.GONE);
                        showRandomQuestion();
                    } else {
                        Intent intent = new Intent(ShenkengMonopoly.this, StationDetailActivity.class);
                        intent.putExtra("stationImage", stationImages); // 圖片
                        intent.putExtra("stationDescription", stationDescriptions[index]); // 描述
                        intent.putExtra("stationIndex", index); // 站點索引
                        startActivityForResult(intent, 1001);

                        hideQuestion(); //按下站點圖片 隱藏題目

                        //呈現相對應的說明
                        aiDialog.setText(stationDescriptions[index]);

                        //先讓background不見，畫面比較乾淨
//                        background.setVisibility(View.GONE);
                    }
                }
            });
        }

        // 🔹 點擊畫面其他地方，隱藏圖片 & 按鈕 (不包括 checkButton)
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() != R.id.checkButton) { // 避免點擊 photoButton 時隱藏
                    mainImage.setVisibility(View.GONE);
                    checkButton.setVisibility(View.GONE);
                    background.setVisibility(View.VISIBLE);
                }
                if (v.getId() != R.id.aiDialog &&
                        v.getId() != R.id.optionA &&
                        v.getId() != R.id.optionB &&
                        v.getId() != R.id.optionC &&
                        v.getId() != R.id.optionD &&
                        v.getId() != R.id.trueButton &&
                        v.getId() != R.id.falseButton) {
                    hideQuestion(); // 隱藏題目與選項
                }
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShenkengMonopoly.this, CheckIn.class);
                startActivity(intent);
            }
        });

        // 依照打卡成功紀錄更新按鈕的邊框
        prefs = getSharedPreferences("checkin_prefs", MODE_PRIVATE);
        Set<String> checkedInStations = prefs.getStringSet("checked_in", new HashSet<>());

        for (String stationIndexStr : checkedInStations) {
            int index = Integer.parseInt(stationIndexStr);
            ImageButton stationButton = getStationButtonByIndex(index);
            if (stationButton != null) {
                stationButton.setBackgroundResource(R.drawable.gold_border); // 加金框
            }
        }

        GameImageViewModel viewModel = new ViewModelProvider(this).get(GameImageViewModel.class);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            int checkedInIndex = data.getIntExtra("stationIndex", -1);
            if (checkedInIndex != -1) {
                ImageButton stationButton = getStationButtonByIndex(checkedInIndex);
                if (stationButton != null) {
                    stationButton.setBackgroundResource(R.drawable.gold_border);
                }
            }
        }
    }

    private int getStationButtonId(int index) {
        switch (index) {
            case 0: return R.id.button_station_1;
            case 1: return R.id.button_station_2;
            case 2: return R.id.button_station_3;
            case 3: return R.id.button_station_4;
            case 4: return R.id.button_station_5;
            case 5: return R.id.button_station_6;
            case 6: return R.id.button_station_7;
            case 7: return R.id.button_station_8;
            case 8: return R.id.button_station_9;
            case 9: return R.id.button_station_10;
            case 10: return R.id.button_station_11;
            case 11: return R.id.button_station_12;
            case 12: return R.id.button_station_13;
            case 13: return R.id.button_station_14;
            case 14: return R.id.button_station_15;
            case 15: return R.id.button_station_16;
            case 16: return R.id.button_station_17;
            default: return -1;
        }
    }

    private ImageButton getStationButtonByIndex(int index) {
        switch (index) {
            case 0: return findViewById(R.id.button_station_1);
            case 1: return findViewById(R.id.button_station_2);
            case 2: return findViewById(R.id.button_station_3);
            case 3: return findViewById(R.id.button_station_4);
            case 4: return findViewById(R.id.button_station_5);
            case 5: return findViewById(R.id.button_station_6);
            case 6: return findViewById(R.id.button_station_7);
            case 7: return findViewById(R.id.button_station_8);
            case 8: return findViewById(R.id.button_station_9);
            case 9: return findViewById(R.id.button_station_10);
            case 10: return findViewById(R.id.button_station_11);
            case 11: return findViewById(R.id.button_station_12);
            case 12: return findViewById(R.id.button_station_13);
            case 13: return findViewById(R.id.button_station_14);
            case 14: return findViewById(R.id.button_station_15);
            case 15: return findViewById(R.id.button_station_16);
            case 16: return findViewById(R.id.button_station_17);
            case 17: return findViewById(R.id.button_station_18);
            default: return null;
        }
    }

    // 提示
    private void showTapTargetView() {
        TapTargetView.showFor(this,
                TapTarget.forView(findViewById(R.id.button_station_18), "\uD83D\uDEA9起點", "點擊右下角的站點開始！依照⬆逆時針方向進行遊戲，站點順序即為阿柔茶文化步道的實際路線。\n\n")
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
                TapTarget.forView(findViewById(R.id.button_station_11), "抽籤功能", "沿途設有趣味抽籤及筊杯問答\n快來挑戰看看吧！\n\n")
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
                    public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                        showThirdTapTargetView(); // 第二個結束才顯示第三個
                    }
                });
    }

    private void showThirdTapTargetView() {
        TapTargetView.showFor(this,
                TapTarget.forView(findViewById(R.id.button_station_12), "\uD83C\uDFAF 景點介紹", "除了豐富有趣的導覽內容，更能打卡留下足跡！\n每成功打卡一次會顯示黃色邊框，累積10個即可兌換深坑小禮物\uD83C\uDF81！\n\n註：虛擬圖片皆為AI生成，請點擊該站點看原始圖片\n\n")
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


    // 問答
    private void showRandomQuestion() {
        Random random = new Random();
        boolean isMultipleChoice = random.nextBoolean();

        aiDialog.setVisibility(View.VISIBLE);

        // 先確保所有選項隱藏
        findViewById(R.id.answerOptions).setVisibility(View.GONE);
        findViewById(R.id.trueFalseLayout).setVisibility(View.GONE);

        if (isMultipleChoice) {
            int index = random.nextInt(multipleChoiceQuestions.length);
            String[] question = multipleChoiceQuestions[index];

            aiDialog.setText(question[0]); // 顯示題目
            findViewById(R.id.answerOptions).setVisibility(View.VISIBLE);

            optionA.setText(question[1]);
            optionB.setText(question[2]);

            if (!question[3].isEmpty()) {
                optionC.setVisibility(View.VISIBLE);
                optionC.setText(question[3]);
            } else {
                optionC.setVisibility(View.GONE);
            }

            if (!question[4].isEmpty()) {
                optionD.setVisibility(View.VISIBLE);
                optionD.setText(question[4]);
            } else {
                optionD.setVisibility(View.GONE);
            }

            setAnswerListener(optionA, "A".equals(question[5]) ? question[1] : "");
            setAnswerListener(optionB, "B".equals(question[5]) ? question[2] : "");
            setAnswerListener(optionC, "C".equals(question[5]) ? question[3] : "");
            setAnswerListener(optionD, "D".equals(question[5]) ? question[4] : "");


        } else {
            int index = random.nextInt(trueFalseQuestions.length);
            String[] question = trueFalseQuestions[index];

            aiDialog.setText(question[0]); //顯示題目
            findViewById(R.id.trueFalseLayout).setVisibility(View.VISIBLE);

            String correctAnswer = question[3].equals("A") ? "是" : "否";

            // 設定是/否按鈕的點擊事件
            setAnswerListener(trueButton, "是".equals(correctAnswer) ? "是" : "否");
            setAnswerListener(falseButton, "否".equals(correctAnswer) ? "否" : "是");
        }
    }

    private void setAnswerListener(Button button, String correctAnswer) {
        button.setOnClickListener(v -> {
            // 取得按鈕的文字內容
            String selectedAnswer = button.getText().toString();

            // 判斷答案是否正確
            boolean isCorrect = selectedAnswer.equals(correctAnswer);

            if (isCorrect) {

                SharedPreferences prefs = getSharedPreferences("checkin_prefs", MODE_PRIVATE);
                Set<String> checkedInStations = prefs.getStringSet("checked_in", new HashSet<>());
                checkedInStations = new HashSet<>(checkedInStations);
                checkedInStations.add(String.valueOf(currentStationIndex));
                prefs.edit().putStringSet("checked_in", checkedInStations).apply();

                ImageButton stationButton = getStationButtonByIndex(currentStationIndex);
                if (stationButton != null) {
                    stationButton.setBackgroundResource(R.drawable.gold_border);
                }

                if (problem == "筊杯"){
                    answerFeedbackImage.setImageResource(R.drawable.picturecorrect); // 正確答案圖片
                    Toast.makeText(this, "回答正確！", Toast.LENGTH_SHORT).show();
                    rootLayout.postDelayed(this::hideQuestion, 1800);
                    rootLayout.postDelayed(() -> background.setVisibility(View.VISIBLE), 1800);
                }else {
                    answerFeedbackImage.setImageResource(R.drawable.picturetrue); // 正確答案圖片
                    Toast.makeText(this, "回答正確！", Toast.LENGTH_SHORT).show();
                    rootLayout.postDelayed(this::hideQuestion, 1800);
                    rootLayout.postDelayed(() -> background.setVisibility(View.VISIBLE), 1800);
                }

            } else {
                if (problem == "筊杯"){
                    answerFeedbackImage.setImageResource(R.drawable.picturewrong); // 錯誤答案圖片
                    Toast.makeText(this, "回答錯誤，再試一次！", Toast.LENGTH_SHORT).show();
                }else {
                    answerFeedbackImage.setImageResource(R.drawable.picturefalse); // 錯誤答案圖片
                    Toast.makeText(this, "回答錯誤，再試一次！", Toast.LENGTH_SHORT).show();
                }
            }

            // 顯示對錯 2秒後隱藏
            answerFeedbackImage.setVisibility(View.VISIBLE);
            answerFeedbackImage.postDelayed(() -> answerFeedbackImage.setVisibility(View.GONE), 1800);
        });
    }

    private void resetAllCheckIns() {
        // 清除 SharedPreferences 中的紀錄
        SharedPreferences prefs = getSharedPreferences("app_preferences", MODE_PRIVATE);
        prefs.edit().putBoolean("has_seen_tap_target", false).apply();

        prefs = getSharedPreferences("station_detail_prefs", MODE_PRIVATE);
        prefs.edit().putBoolean("has_seen_station_tutorial", false).apply();

        prefs = getSharedPreferences("checkin_prefs", MODE_PRIVATE);
        prefs.edit().remove("checked_in").apply();


        // 移除所有按鈕的金邊
        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton stationButton = findViewById(buttonIds[i]);
            if (stationButton != null) {
                stationButton.setBackgroundResource(0); // 或改成原本的背景
            }
        }

    }

    private void hideQuestion() {
        // by伍321aiDialog.setText("說明欄");
        aiDialog.setVisibility(View.GONE); //by伍321
        findViewById(R.id.answerOptions).setVisibility(View.GONE);
        findViewById(R.id.trueFalseLayout).setVisibility(View.GONE);

        //by伍321 mainImage.setVisibility(View.VISIBLE);
    }

    // by 林 0528
    private void copyDatabaseIfNeeded(Context context) {
        File dbFile = context.getDatabasePath("stations.db");

        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();

            try (InputStream is = context.getAssets().open("stations.db");
                 OutputStream os = new FileOutputStream(dbFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}