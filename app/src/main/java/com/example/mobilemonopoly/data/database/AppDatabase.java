/* 0206 新增 by 孟*/
package com.example.mobilemonopoly.data.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;
import java.util.concurrent.Executors;
import com.example.mobilemonopoly.data.model.GameImage;


@Database(entities = {GameImage.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract GameImageDao gameImageDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .addCallback(roomCallback) // 加入 callback
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override

        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // 預設資料在資料庫建立時插入
            Executors.newSingleThreadExecutor().execute(() -> {
                if (instance != null && instance.isOpen()) {
                    GameImageDao dao = instance.gameImageDao();
                    dao.insert(new GameImage("picture1", "/main/res/drawable/picture1.png", "涼亭1", 1));
                    dao.insert(new GameImage("picture2", "/main/res/drawable/picture2.png", "豆娘", 2));
                    dao.insert(new GameImage("picture3", "/main/res/drawable/picture3.png", "擲茭", 3));
                    dao.insert(new GameImage("picture4", "/main/res/drawable/picture4.png", "瓢蟲", 4));
                    dao.insert(new GameImage("picture5", "/main/res/drawable/picture5.png", "涼亭2", 5));
                    dao.insert(new GameImage("picture6", "/main/res/drawable/picture6.png", "葉杉", 6));
                    dao.insert(new GameImage("picture7", "/main/res/drawable/picture7.png", "抽籤", 7));
                    dao.insert(new GameImage("picture3", "/main/res/drawable/picture3.png", "擲茭", 8));
                    dao.insert(new GameImage("picture9", "/main/res/drawable/picture9.png", "黃雀", 9));
                    dao.insert(new GameImage("picture10", "/main/res/drawable/picture10.png", "月桂", 10));
                    dao.insert(new GameImage("picture11", "/main/res/drawable/picture11.png", "某花", 11));
                    dao.insert(new GameImage("picture12", "/main/res/drawable/picture12.png", "香魚", 12));
                    dao.insert(new GameImage("picture7", "/main/res/drawable/picture7.png", "抽籤", 13));
                    dao.insert(new GameImage("picture14", "/main/res/drawable/picture14.png", "大花咸豐草", 14));
                    dao.insert(new GameImage("picture15", "/main/res/drawable/picture15.png", "九芎樹", 15));
                    dao.insert(new GameImage("picture5", "/main/res/drawable/picture5.png", "涼亭2", 16));
                    dao.insert(new GameImage("picture17", "/main/res/drawable/picture17.png", "紫色花", 17));
                    dao.insert(new GameImage("picture7", "/main/res/drawable/picture7.png", "抽籤", 18));
                    dao.insert(new GameImage("picture20", "/main/res/drawable/picture20.png", "某花2", 19));
                }
            });
        }
    };
}