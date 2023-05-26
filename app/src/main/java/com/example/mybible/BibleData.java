package com.example.mybible;

import android.content.Context;

import com.example.mybible.db.BibleDataEntity;
import com.example.mybible.db.RoomDB;

import java.util.List;

public class BibleData {
    RoomDB database;
    Context context;

    public BibleData(Context context) {
        this.context = context;
    }

    public List<BibleDataEntity> getBibleData(String fixDoc, String fixChapter) {
        database = RoomDB.getInstance(context);
        List<BibleDataEntity> dbData = database.bibleDataDao().getBibleEntity(fixDoc, fixChapter);
        return dbData;
    }
}
