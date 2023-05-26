package com.example.mybible.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memo_entity")
public class MemoDataEntity {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "doc")
    private String doc;

    @ColumnInfo(name = "chapter")
    private String chapter;

    @ColumnInfo(name = "memo")
    private String memo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
