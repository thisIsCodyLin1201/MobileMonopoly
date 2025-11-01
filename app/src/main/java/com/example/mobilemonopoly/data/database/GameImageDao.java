/* 0213 新增 by 孟*/
package com.example.mobilemonopoly.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mobilemonopoly.data.model.GameImage;

import java.util.List;

@Dao
public interface GameImageDao {
    @Insert
    void insert(GameImage gameImage);

    @Insert
    void insertAll(GameImage... gameImages);

    @Delete
    void delete(GameImage gameImage);

    @Query("SELECT * FROM game_images WHERE position = :position")
    GameImage getImageByPosition(int position);

    @Query("SELECT * FROM game_images")
    List<GameImage> getAllImages();
}
