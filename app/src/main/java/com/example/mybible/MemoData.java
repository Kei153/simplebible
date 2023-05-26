package com.example.mybible;

import android.content.Context;

import com.example.mybible.db.MemoDataEntity;
import com.example.mybible.db.RoomDB;

public class MemoData {
    RoomDB database;
    Context context;

    public MemoData(Context context) {
        this.context = context;
    }

    public MemoDataEntity getMemoData(String doc, String chapter){
        database = RoomDB.getInstance(context);
        MemoDataEntity memoData = database.memoDataDao().getMemoData(doc, chapter);
        return memoData;
    }
}
