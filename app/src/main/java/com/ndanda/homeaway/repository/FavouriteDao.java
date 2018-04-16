package com.ndanda.homeaway.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ndanda.homeaway.data.events;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteEvent(events event);

    @Query("SELECT * FROM events")
    LiveData<List<events>> getFavoriteEvents();
}
