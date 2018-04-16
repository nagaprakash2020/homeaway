package com.ndanda.homeaway.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ndanda.homeaway.data.events;

@Database(entities = {events.class},version = 1,exportSchema = false)
public abstract class HomeAwayDatabase extends RoomDatabase{

    public abstract FavouriteDao favouriteDao();
}
