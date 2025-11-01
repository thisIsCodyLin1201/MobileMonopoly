package com.example.mobilemonopoly;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class LearnMore extends AppCompatActivity {

    private FrameLayout frameLayout;
    private ImageView stationImage;
    private TextView descriptionText;
    private int stationIndex;
    private int[][] stationImages;
    private Button backButton;

    private Map<Integer, List<FeatureSpot>> featureSpotsByStation = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

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

        frameLayout = findViewById(R.id.learnMoreLayout);
        stationImage = findViewById(R.id.learnMoreImage);
        backButton = findViewById(R.id.backButton);
        descriptionText = findViewById(R.id.learnMoreText);

        backButton.setOnClickListener(v -> finish());

        stationImage.setOnTouchListener((v, event) -> {
            float x = event.getX();
            float y = event.getY();
            //Toast.makeText(LearnMore.this, "X: " + x + ", Y: " + y, Toast.LENGTH_SHORT).show();
            return false;
        });

        Intent intent = getIntent();
        stationIndex = intent.getIntExtra("stationIndex", -1);
//        stationImages = (int[][]) intent.getSerializableExtra("stationImage");


        int[][] stationImages = {
                {R.drawable.aipicture_2}, // 站點1的圖片 1792茶園
                {R.drawable.aipicture_3}, // 站點2的圖片 隱修院
                {R.drawable.aipicture_4}, // 站點3的圖片 筊杯
                {R.drawable.aipicture_5}, // 站點4的圖片 向天湖
                {R.drawable.aipicture_1}, // 站點5的圖片 旗山老母
                {R.drawable.original_7}, // 站點6的圖片 抽籤
                {R.drawable.aipicture_6}, // 站點7的圖片 阿柔洋產業道路
                {R.drawable.original_8}, // 站點8的圖片   青山香草
                {R.drawable.aipicture_10}, // 站點9的圖片  筊杯
                {R.drawable.aipicture_11}, // 站點10的圖片 親水公園
                {R.drawable.aipicture_12}, // 站點11的圖片 石媽祖
                {R.drawable.aipicture_13}, // 站點12的圖片 抽籤
                {R.drawable.aipicture_15}, // 站點14的圖片 大旗領步道口
                {R.drawable.aipicture_14}, // 站點13的圖片 金城茶園
                {R.drawable.aipicture_16}, // 站點15的圖片 天南宮
                {R.drawable.aipicture_17},  // 站點16的圖片 猴山岳步道(雙扇絕)
                {R.drawable.aipicture_18},  // 站點17的圖片 深坑木柵交界處
                {R.drawable.aipicture_20},  // 站點18的圖片 106 club house

        };

        if (stationIndex < 0 || stationImages == null || stationIndex >= stationImages.length) {
            finish();
            return;
        }

        stationImage.setImageResource(stationImages[stationIndex][0]);

        setupFeatureData();

        List<FeatureSpot> spots = featureSpotsByStation.get(stationIndex);
        if (spots != null) {
            for (FeatureSpot spot : spots) {
                showBlinkingCircle(spot);
            }
        }
    }

    private void setupFeatureData() {

        featureSpotsByStation.put(0, Arrays.asList(
                new FeatureSpot(0.11f, 0.58f, 0, R.drawable.spot9_1, "魔鏡呀魔鏡 誰是北台灣最早的茶園?", "你知道1792茶園是北部歷史最悠久的茶園嗎? 為了讓深坑茶香飄得更遠，農會動手改良製茶流程，從設備升級、技術創新到研發符合年輕人口味的新茶包。目標是讓你泡出來的每一口茶，香氣不只撲鼻，還能撩人心弦～誰說傳統茶不能走潮路線？深坑茶就是要傳統中帶點新潮"),
                new FeatureSpot(0.5009f, 0.4029f,  1, R.drawable.spot9_2, "阿水的種茶人生：從門外漢變特等獎茶神", "1792茶園創辦人黃土水，當初不會種茶，只會喝茶。結果一頭栽進茶園人生，他自零開始學習種茶，並親自投入茶園的開墾與管理，四年後靠實力拿下2024包種茶特等獎，證明一句老話：人生苦短，喝好茶不如種好茶！"),
                new FeatureSpot(0.70f, 0.75f,  2, R.drawable.spot9_3, "茶香＋腦袋＝行銷全場！老茶新玩法", "茶葉再香，還是得靠「外表」衝出市場。深坑茶未來將有「高顏值」專屬包裝亮相，圖案融合在地文化，讓你一眼就認出這是深坑出品；量小的也不怕，走公版印刷路線，一樣有型又實惠。配合觀光導覽、茶葉專區、聯名茶飲點心，邊玩邊喝邊買，誰能抗拒這種有故事又好喝的深坑茶？")
        ));
        featureSpotsByStation.put(1, Arrays.asList(
                new FeatureSpot(0.639f, 0.843f,  0, R.drawable.spot8_1, "禮拜堂不是打卡景點，但靈魂真的能充電", "每天早上彌撒準時開場，修女微笑點頭不多話，信徒能來參加，但別期待有熱情招呼，修女們微微點頭後就飄走，默默回去工作。彌撒場地不大，但莊嚴氛圍滿分，進門彷彿自帶沉靜濾鏡——唯一要努力的，是先爬上那個坡。"),
                new FeatureSpot(0.20f, 0.20f,  1, R.drawable.spot8_2, "她們的苦修日常：從祈禱到綁鋼筋", "隱修院的特色，就是所有修女都要做工，不只是祈禱戰士，更是農夫、廚娘、水泥工。她們自己種菜、煮飯、打掃，甚至砌磚挑砂石樣樣來，完全自給自足。別懷疑，她們不是在練生存遊戲。"),
                new FeatureSpot(0.5f, 0.597f,  2, R.drawable.spot8_3, "隱修院血統傳承，來自美國的虔誠", "1954年從美國遠道而來的八位修女，在新竹創立母院，之後拓展到深坑、嘉義。院長是高齡 98歲的「保拉姆姆」，屬於聖衣修女會，衣服是咖啡色的，頭戴黑色頭巾的就是發過「終身願」的正式修女，若是還沒有發願的，頭巾帶白色的。")
        ));
        featureSpotsByStation.put(3, Arrays.asList(
                new FeatureSpot(0.60f, 0.3f,  0, R.drawable.spot6_1, "走進山林 Instagram：\n這裡的植物比你朋友還上鏡", "向天湖曾是昔日通往筆架山的農業道路，沿路像走進大自然的美顏濾鏡，月桃、蕨類、百年石頭屋、紅磚屋齊上陣，還有戴「綠圍裙」的桫欏熱情迎賓。每走一步，都是生態大觀園，動植物排排站歡迎你！"),
                new FeatureSpot(0.40f, 0.5f,  1, R.drawable.spot6_2, "不是湖卻叫向天湖？這名字騙很大！", "向天湖真的是湖嗎？別被名字唬了，這裡是山中谷地＋梯田的混搭美景，日領時期軍事空偵，發現水田迴光猶如山間小湖，向天湖今名自此不脛而走，才有了這個浪漫名字。老祖宗們爬山開墾順便取名，也太會了吧！"),
                new FeatureSpot(0.783f, 0.854f,  2, R.drawable.spot6_3, "田埂中的流星雨 滿山遍野的螢火蟲", "每年 3 到 5 月，向天湖變成螢火蟲的天堂，滿山遍野的閃爍螢光，為夜晚增添夢幻氛圍。此外，這裡的環境維護良好，能見到翡翠樹蛙、五色鳥、台灣藍鵲等豐富動植物生態。")
        ));
        featureSpotsByStation.put(4, Arrays.asList(
                new FeatureSpot(0.398f, 0.2563f,  0, R.drawable.spot10_1, "你夢過中樂透，他夢到神仙降臨！", "深坑蔡宮主原本是貿易老闆，某天在夢裡遇見兩位仙尊：「驪山老母」和「太上道祖」，還指定要「進駐」他家。連續夢了幾個月，他才驚覺這不是普通夢。於是依夢中形象找師傅雕像、開光奉祀，人生直接從貿易轉行開宮，這轉職比轉系還硬核。"),
                new FeatureSpot(0.431f, 0.745f,  1, R.drawable.spot10_2, "神仙開會所！這座山中宮廟陣容超華麗", "深坑的這座神殿不是普通小廟，從驪山老母起家，接著請來瑤池金母、九天玄母、武財神… 甚至還有樊梨花大元帥，陣容比跨年演唱會還強。傳說在安座大典當天還有「群鷹雲集」在天飛舞，根本就是開天窗直播現場，神蹟滿點！"),
                new FeatureSpot(0.655f, 0.45f,  2, R.drawable.spot10_3, "《我與神明的房地產人生》選廟地比買房還曲折！", "創建宮廟不是說蓋就蓋，從台東果園飛來飛去，到苗栗關刀山談判破局，再回頭深坑比地點、談地主、標會借款、簽約買地……最後選定現址時，簡直是天時地利人合，連地主都剛好想賣，果然神明指定地點就是準。")
        ));
        featureSpotsByStation.put(6, Arrays.asList(
                new FeatureSpot(0.729f, 0.204f,  0, R.drawable.spot12_1, "\uD83C\uDF38 春天像掉進櫻花仙境", "每年三月，阿柔洋開啟粉嫩濾鏡模式，櫻花開到你手機記憶體崩潰。是散步天堂，更是打卡地獄——因為你會忍不住拍、一直拍、狂拍不止！"),
                new FeatureSpot(0.353f, 0.750f,  1, R.drawable.spot12_2, "\uD83C\uDF3F夏天是綠色健身房", "這條道路是單車愛好者必訪之地，以其綿延的緩升坡聞名，是大腿的夢魘也是靈魂的淨化儀式。汗流滿面時抬頭一看，天啊——原來天堂是這種翠綠調！"),
                new FeatureSpot(0.136f, 0.391f,  2, R.drawable.spot12_3, "\uD83C\uDF2B 冬天雲霧繚繞像進仙境副本", "冬晨雲霧繚繞，變化萬千，景色迷人。清晨上路，雲霧飄渺，踩一踩就像在「飛騎凌雲」，彷彿下秒會有仙人對你說：「凡人，你修行得不錯，騎腳踏車真勇健。」")
        ));
        featureSpotsByStation.put(7, Arrays.asList(
                new FeatureSpot(0.198f, 0.497f,  0, R.drawable.spot13_1, "荷包茶不是荷包蛋，魚腥草茶有腥味嗎?", "園主燕子的家族已經在這片土地上耕耘近二十代，傳承著文山包種茶的種植技藝。但她不僅僅止步於此，而是透過自修與遠行學習，將香草與傳統茶文化結合，創造出獨特的香草養生茶飲，如薄荷包種茶、魚腥草茶等。"),
                new FeatureSpot(0.587f, 0.281f,  1, R.drawable.spot13_2, "政大茶  青春靈感的融合滋味", "政大學生參訪時靈機一動，建議將薄荷與包種茶結合，激發出園主創作出「政大茶」。另有香草咖啡、香草冰品等，大受好評，提供遊客及團體預約體驗品嘗。"),
                new FeatureSpot(0.758f, 0.654f,  2, R.drawable.spot13_3, "颱風天的打工換宿 以工換茶", "40歲的園主燕子回歸故里，承襲祖業，並邀請來訪的遊客親身參與農作，甚至在颱風過後，透過「以工換茶」的方式，共同修復農園景觀。")
        ));
        featureSpotsByStation.put(9, Arrays.asList(
                new FeatureSpot(0.35f, 0.25f,  0, R.drawable.spot5_1, "阿柔坑溪的天然SPA", "水清、石階、還有涼亭 阿柔坑溪藏匿在青翠的山林中，水清得像玻璃，沿著石階滾滾流下，簡直像是大自然專門為你設計的按摩水療。來到這裡，放下城市的壓力，走在這些天然石階上，感覺就像走進了天然SPA中心。"),
                new FeatureSpot(0.75f, 0.25f,  1, R.drawable.spot5_2, "仁者樂山 智者樂水 一起玩水成為智者", "對當地人來說，阿柔坑溪不只是風景，更是玩水的天堂。他們在水中嬉戲，任水流輕觸，這種親密關係根本是生活的一部分。坐在涼亭的你心裡是否默默想：「嗯我應該也來場水上運動了」"),
                new FeatureSpot(0.40f, 0.80f,  2, R.drawable.spot5_3, "遠離城市，來場水聲療癒", "如果你還在尋找逃離都市喧囂的好地方，那阿柔坑溪絕對是你的理想選擇。這裡的溪水不僅清澈，還有那無比放鬆的潺潺聲，仿佛每個音符都在告訴你：「放輕鬆，別再想工作了!」")
        ));
        featureSpotsByStation.put(10, Arrays.asList(
                new FeatureSpot(0.85f, 0.65f,  0, R.drawable.spot4_1, "媽祖也愛健走的香香步道", "石媽祖步道入口在阿柔洋，來回 30 分鐘剛剛好，還有桂花香當天然香氛機。走著走著，香味撲鼻，腳步都變輕盈，適合親子健行，媽祖可能也想跟著散步！"),
                new FeatureSpot(0.4f, 0.49f,  1, R.drawable.spot4_2, "古碑立誓：百年前就有的募資", "途中遇到一塊 昭和五年古碑，是先人們的「募資建設紀錄」。當年香客太多，山路泥濘難行，大家遂集資鋪橋蓋路，最大捐款 50 圓，最小 6 圓，黃姓捐最多，堪稱「鋪路王」！"),
                new FeatureSpot(0.20f, 0.50f,  2, R.drawable.spot4_3, "石媽祖傳奇：一塊石頭變媽祖", "古道全長約三十分鐘路程，最終可達鎮南宮，此地供奉的不是神像，而是一塊風化而形似媽祖的巨石。原名「水南宮」，是一塊超像媽祖的石頭！據說有年水災，媽祖托夢要改名「鎮南宮」，從此神威顯赫，香火鼎盛。這才是真正的「石來運轉」！")
        ));
        featureSpotsByStation.put(12, Arrays.asList(
                new FeatureSpot(0.70f,  0.70f, 0, R.drawable.spot1_1, "古道風華再現", "深坑的古道如時光機，帶我們回到先民的開墾歲月。雖然許多古道已被產業道路取代，但大崎嶺步道依然屹立不搖，保留樸實風貌，甚至因土石流意外「去人工化」，還原了最初的樣貌。彷彿穿越回百年前的深坑秘境，這裡才是真正的復古風！"),
                new FeatureSpot(0.35f, 0.80f,  1, R.drawable.spot1_2, "山上小孩的「極限通學」之路", "大崎嶺步道曾是深坑孩子們的「通學步道」，以前沒有產業道路，孩子們只能靠雙腿翻山越嶺去上學。這條步道曾是孩子們的「上學捷徑」，冬天摸黑打著 電土燈和煤油火把 衝學校，回程還得揹鹽米、運汽水賺零用錢。甚至沿路拖相思木去換錢，真正的「登山打工組」！"),
                new FeatureSpot(0.20f,  0.45f, 2, R.drawable.spot1_3, "從喘息到悠閒，倒吃甘蔗的歷史自然寶庫", "大崎嶺步道不只是歷史隧道，還是自然寶庫。全長 1600 公尺，前半段陡上、後半段緩坡，走起來有如「倒吃甘蔗」，讓你一路從「喘爆模式」切換成「愜意散步」。這裡的綠蔭濃密，夏天來走完全不會被曬成「紅燒」，而且步道整備完善，方向明確，不怕迷路變成「荒野求生」。")
        ));
        featureSpotsByStation.put(13, Arrays.asList(
                new FeatureSpot(0.237f, 0.206f,  0, R.drawable.spot2_1, "茶園誕生的那天，他只是想喝杯好茶", "有一天，金城老師喝到一杯香到讓他眼睛發亮的茶，忍不住大喊：「這茶也太好喝了吧！」三天後，他就把自家的竹筍園改成茶園，堅持友善無毒種金萱、青心烏龍，還自創品牌「金城茶園」。喝茶變種茶，人生轉彎只花了一杯的時間。"),
                new FeatureSpot(0.554f, 0.517f,  1, R.drawable.spot2_2, "從畫家變茶農，他的畫布現在是茶園", "金城老師斜槓工作是愛畫山畫海的藝術家，手上拿的應該是畫筆。但自從開始種茶，畫筆變成了剪刀和除草刀，心中最浪漫的事，就是做出又香又甘的深坑茶啊！結合好奇玩心、利他真心、藝術用心三種心境，成就一座浪漫精緻茶園。"),
                new FeatureSpot(0.185f, 0.737f,  2, R.drawable.spot2_3, "誰說一定要工業革命? 用雙手種好茶", "金城老師的茶園管理，簡直像養寵物一樣。他對茶葉品種、種植、採摘等一切都瞭若指掌，甚至能跟茶葉講心事！有時他也會笑說，自己的茶園不像那些大規模導入「工業革命」的茶園，他更注重能從小而美的規模中，以人工為主，輕機械為輔，琢磨與大自然共生共容的方式。")
        ));
        featureSpotsByStation.put(14, Arrays.asList(
                new FeatureSpot(0.153f, 0.200f,  0, R.drawable.spot15_1, "三太子遠渡重洋來深坑，三百年香火不斷", "別看天南宮低調，這裡供奉的三太子可是資深移民——早在清嘉慶年間就從福建安溪搬來台灣，香火傳了三百多年，比你阿公的阿公還資深！每年農曆九月九，信徒會聚集來過生日，氣氛比跨年還熱鬧。"),
                new FeatureSpot(0.847f, 0.229f, 1,  R.drawable.spot15_2, "神廟變打卡點，香火與山景齊飛", "今天的天南宮不只是信仰中心，更是登山客與單車族的中繼站。爬累了？這裡可以休息賞景、俯瞰台北盆地。拜拜祈福兼散步，從古到今，一樣神清氣爽！"),
                new FeatureSpot(0.498f, 0.728f,  2, R.drawable.spot15_3, "神明夢中導航，荒山也能變金廟", "民國 73 年，元帥顯靈降駕在茶農高清正身上，指點建廟濟世。雖然山上沒電、沒路、沒水，但三太子夢中報地址：「三公里外的石壁有泉水。」大家一找，真的找到了！建廟過程也一路順利，神蹟傳開。")
        ));
        featureSpotsByStation.put(15, Arrays.asList(
                new FeatureSpot(0.213f, 0.363f,  0, R.drawable.spot16_1, "迷路了怎麼辦? 不怕雙扇蕨罩著你", "雙扇蕨是台灣唯一的雙扇蕨科植物，因保有侏儸紀時代的特徵，被稱為「活化石」。它通常生長在淺薄岩壁，外形像兩支破摺扇，邊緣像撕裂的雨傘，因此也叫「破傘蕨」。由於向陽特性，它也被譽為「大自然的指南針」。"),
                new FeatureSpot(0.506f, 0.526f,  1, R.drawable.spot16_2, "侏儸紀活化石，雙扇蕨不容錯過！", "雙扇蕨，這個被譽為「活化石」的奇特植物，是台灣唯一的雙扇蕨科代表，擁有超過一億年的歷史！彷彿穿越回恐龍時代，它甚至還保有侏儸紀時代的特徵。"),
                new FeatureSpot(0.113f, 0.837f,  2, R.drawable.spot16_3, "破傘蕨？不，它是自然界的時光機！", "當你看見這顆破傘蕨，長長的葉柄像是無數的稻桿，葉子則被精細地分裂頂端微尖，外型像兩把撕裂的雨傘，展開來就像是大自然的指引，幾乎每一片葉子都能當“指南針”看待，指引著你探索大自然的奧妙。")
        ));
        featureSpotsByStation.put(16, Arrays.asList(
                new FeatureSpot(0.182f, 0.611f,  0, R.drawable.spot17_1, "東邊日出西邊雨，雙北換日線", "你知道嗎？深坑其實是新北市和台北市的交界地，而你現在站的地方，左邊是新北的深坑，右邊是台北的文山，但是！行政區的指標牌子卻插反邊了，你注意到了嗎？"),
                new FeatureSpot(0.80f, 0.85f,  1, R.drawable.spot17_2, "深坑四寶：豆腐與茶葉，絕對是你味蕾的新冒險！", "手工製作，水質甘甜，豆腐無論是紅燒、油炸還是糖醋、鹽滷法、木炭火加熱，讓每一塊豆腐都帶著焦香，柔嫩又有彈性。還有深坑的茶葉，它可是台灣最早栽種的地區之一，從清朝時期就開始外銷，成為文山地區的茶葉集散中心。來深坑，不只是吃，更是一場穿越百年歷史的味覺之旅！"),
                new FeatureSpot(0.573f, 0.497f,  2, R.drawable.spot17_3, "深坑四寶: 還有黑豬肉和竹筍，等你來解鎖！", "優質綠竹筍挑選訣竅為「四無」及「四有」，四無為無出青、無受傷、無漂白及無臭水管味。四有為竹筍有形（牛角形）、籜葉有緊、纖維有細及筍底有白。而這些黑豬可是用回收的廚餘經高溫烹煮冷卻後餵食，肉質結實又富有彈性，讓每一口都充滿力量。")
        ));
        featureSpotsByStation.put(17, Arrays.asList(
                new FeatureSpot(0.40f, 0.73f,  0, R.drawable.spot18_1, "騎士的秘密基地", "隱藏在台 106 線旁的 106 Club HOUSE，是一處深受鐵馬好手喜愛的驛站，陳春發董事長透過自身的國外旅遊經驗，將對世界各地單車文化的見聞投射到深坑。106 Club HOUSE，不只是補給站，更是車友的情報交換中心！來阿柔茶文化步道，至少會看到一個腳踏車騎士！"),
                new FeatureSpot(0.80f, 0.49f,  1, R.drawable.spot18_2, "最硬派的訓練場", "緊鄰一旁的阿柔產業道路，蜿蜒、曲折、坡陡、彎急，擁有連續的上坡與彎路，不論是平日或假日，這條山路上經常可見自行車手用此地的天然地形進行訓練。但對當地阿伯來說，這裡只是一條日常買菜的小徑。"),
                new FeatureSpot(0.61f, 0.40f,  2, R.drawable.spot18_3, "鐵馬天堂進化中", "深坑不只讓你騎車，還要讓你騎得舒適！公所努力打造單車友善環境、設施與標誌，讓更多人透過自行車旅遊深入探索深坑的自然風貌與地方文化，未來這裡可能變成台北近郊最夯的「鐵馬勝地」！")
        ));
    }

    private void showBlinkingCircle(FeatureSpot spot) {
        stationImage.post(() -> {
            Drawable drawable = stationImage.getDrawable();
            if (drawable == null) return;

            int imageWidth = drawable.getIntrinsicWidth();
            int imageHeight = drawable.getIntrinsicHeight();

            float scaleX = (float) stationImage.getWidth() / imageWidth;
            float scaleY = (float) stationImage.getHeight() / imageHeight;
            float scale = Math.min(scaleX, scaleY);

            float offsetX = (stationImage.getWidth() - imageWidth * scale) / 2;
            float offsetY = (stationImage.getHeight() - imageHeight * scale) / 2;

            // ✅ 這裡 x/y 是 0~1 的比例，乘上圖片實際尺寸再乘縮放
            float realX = spot.x * imageWidth * scale + offsetX;
            float realY = spot.y * imageHeight * scale + offsetY;

            // 建立圓點（🔴 固定大小，建議 50dp，可依需求調整）
            int dpSize = 25;
            float density = getResources().getDisplayMetrics().density;
            int circleSize = (int) (dpSize * density);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(circleSize, circleSize);
            params.leftMargin = (int) realX - circleSize / 2;
            params.topMargin = (int) realY - circleSize / 2;


            View circle = new View(this);
            circle.setLayoutParams(params);
            circle.setBackgroundResource(R.drawable.circle_shape);

            AlphaAnimation blink = new AlphaAnimation(0.1f, 1.0f);
            blink.setDuration(500);
            blink.setRepeatMode(AlphaAnimation.REVERSE);
            blink.setRepeatCount(AlphaAnimation.INFINITE);
            circle.startAnimation(blink);

            circle.setOnClickListener(v -> {
                Intent intent = new Intent(LearnMore.this, FeatureDetailActivity.class);
                intent.putExtra("spotIndex", spot.index);
                intent.putExtra("imageResId", spot.imageResId);
                intent.putExtra("title", spot.title);
                intent.putExtra("description", spot.description);
                intent.putExtra("spotList", new ArrayList<>(featureSpotsByStation.get(stationIndex)));
                startActivity(intent);
            });

            frameLayout.addView(circle);
            descriptionText.setText("想知道更多故事嗎？快點擊圓點吧！");
        });
    }



//    private void showBlinkingCircle(FeatureSpot spot) {
//        View circle = new View(this);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(60, 60);
//        params.leftMargin = (int) spot.x;
//        params.topMargin = (int) spot.y;
//        circle.setLayoutParams(params);
//        circle.setBackgroundResource(R.drawable.circle_shape); // 設定圓形外觀
//
//        // 加上閃爍動畫
//        AlphaAnimation blink = new AlphaAnimation(0.1f, 1.0f);
//        blink.setDuration(500);
//        blink.setRepeatMode(AlphaAnimation.REVERSE);
//        blink.setRepeatCount(AlphaAnimation.INFINITE);
//        circle.startAnimation(blink);
//
//        // 點擊事件：跳轉到介紹頁
//        circle.setOnClickListener(v -> {
//            //Log.d("LearnMore", "Selected Spot Index: " + spot.index); // 在這裡檢查選擇的 spotIndex
//            Intent intent = new Intent(LearnMore.this, FeatureDetailActivity.class);
//            intent.putExtra("spotIndex", spot.index);
//            intent.putExtra("imageResId", spot.imageResId);
//            intent.putExtra("title", spot.title);
//            intent.putExtra("description", spot.description);
//            intent.putExtra("spotList", new ArrayList<>(featureSpotsByStation.get(stationIndex)));
//            startActivity(intent);
//        });
//        // 把圈圈加到畫面上
//        frameLayout.addView(circle);
//        descriptionText.setText("想知道更多故事嗎？快點擊圓點吧！");
//    }























//    private ImageView topImage, bottomImage;
//    private View topOverlay, bottomOverlay;
//    private TextView textDescription;
//    private int[][] stationImages;
//    private int stationIndex;
//    private Button backButton;
//    private List<Spot> correctSpots;  // 存放正確點擊區域
//    private SpotDrawingView overlayView;  // 繪製圓圈的自訂 View
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_learn_more);
//
//        topImage = findViewById(R.id.topImage);
//        bottomImage = findViewById(R.id.bottomImage);
//        textDescription = findViewById(R.id.textDescription);
//        backButton = findViewById(R.id.backButton);
//        overlayView = findViewById(R.id.overlayView);  // 負責畫圓圈
//
//        backButton.setOnClickListener(v -> finish());
//
//        // 接收 Intent 資料
//        Intent intent = getIntent();
//        stationIndex = intent.getIntExtra("stationIndex", -1);
//        stationImages = (int[][]) intent.getSerializableExtra("stationImage");
//
//        if (stationIndex < 0 || stationIndex >= stationImages.length) {
//            Toast.makeText(this, "❌ 無效的站點資訊！", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        topImage.setImageResource(stationImages[stationIndex][0]);
//        bottomImage.setImageResource(stationImages[stationIndex][1]);
//
//        // 初始化正確點擊區域
//        initializeSpots();
//
//        // 設定點擊監聽
//        bottomImage.setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                handleTouch(event.getX(), event.getY());
//            }
//            return true;
//        });
//    }
//
//    // 設定不同站點的正確點擊範圍
//    private void initializeSpots() {
//        correctSpots = new ArrayList<>();
//
//        if (stationIndex == 0) {
//            correctSpots.add(new Spot(50, 50, 50, "這裡是古老的橋樑"));
//            correctSpots.add(new Spot(50, 50, 60, "這棵樹已有百年歷史"));
//        } else if (stationIndex == 1) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 2) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        } else if (stationIndex == 4) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 5) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 8) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 10) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 11) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 13) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 14) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 15) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 16) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 17) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }else if (stationIndex == 19) {
//            correctSpots.add(new Spot(220, 300, 45, "這是特色雕像"));
//            correctSpots.add(new Spot(500, 550, 50, "這是歷史建築"));
//            correctSpots.add(new Spot(700, 200, 55, "這座塔象徵著當地文化"));
//        }
//    }
//
//    // 處理圖片點擊事件
//    private void handleTouch(float x, float y) {
//        int[] location = new int[2];
//        bottomImage.getLocationOnScreen(location);
//        float relativeX = x - location[0];
//        float relativeY = y - location[1];
//        float xlocation = location[0] ;
//        float ylocation = location[1] ;
//
//        boolean isCorrect = false;
//        for (Spot spot : correctSpots) {
//            if (spot.isInside(relativeX, relativeY)) {
//                isCorrect = true;
//                textDescription.setText(spot.description);
//                overlayView.addSpot(relativeX, relativeY, false); // 在下方圖片畫圈
//                overlayView.addSpot(spot.x, spot.y, true); // 在上方圖片畫圈
//                break;
//            }
//        }
//
//        if (!isCorrect) {
//            Toast.makeText(this, "❌ 點錯了！請再試一次！", Toast.LENGTH_SHORT).show();
//            textDescription.setText("X: " + xlocation + " Y: " + ylocation);//每點一次，座標要重跑
//        }
//    }
//    // **🔹 內部類別 1: 定義 Spot (點擊區域)**
//    private class Spot {
//        float x, y, radius;
//        String description;
//
//        public Spot(float x, float y, float radius, String description) {
//            this.x = x;
//            this.y = y;
//            this.radius = radius;
//            this.description = description;
//        }
//
//        // 檢查點擊是否在範圍內
//        public boolean isInside(float touchX, float touchY) {
//            //return Math.sqrt(Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2)) <= radius;
//            return true;
//        }
//    }


}
