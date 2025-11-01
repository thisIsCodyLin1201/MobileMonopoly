/* 0213 新增 by 孟*/
package com.example.mobilemonopoly.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.mobilemonopoly.data.model.GameImage;
import com.example.mobilemonopoly.data.repository.GameImageRepository;
import androidx.annotation.NonNull;
import java.util.List;

public class GameImageViewModel extends AndroidViewModel {
    private GameImageRepository repository;

    public GameImageViewModel(@NonNull Application application) {
        super(application);
        repository = new GameImageRepository(application);
    }

    public void insert(GameImage gameImage) {
        repository.insert(gameImage);
    }

    public List<GameImage> getAllImages() {
        return repository.getAllImages();
    }
}
