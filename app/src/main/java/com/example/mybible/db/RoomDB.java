package com.example.mybible.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BibleDataEntity.class, MemoDataEntity.class}, version = 3)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "BibleData.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract BibleDataDao bibleDataDao();
    public abstract MemoDataDao memoDataDao();
}
