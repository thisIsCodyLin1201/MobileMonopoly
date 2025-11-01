/* 0213 新增 by 孟*/
package com.example.mobilemonopoly.data.repository;

import android.app.Application;

import com.example.mobilemonopoly.data.database.AppDatabase;
import com.example.mobilemonopoly.data.database.GameImageDao;
import com.example.mobilemonopoly.data.model.GameImage;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameImageRepository {
    private GameImageDao gameImageDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public GameImageRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        gameImageDao = db.gameImageDao();
    }

    public void insert(GameImage gameImage) {
        executorService.execute(() -> gameImageDao.insert(gameImage));
    }

    public GameImage getImageByPosition(int position) {
        return gameImageDao.getImageByPosition(position);
    }

    public List<GameImage> getAllImages() {
        return gameImageDao.getAllImages();
    }
}
