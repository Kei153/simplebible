package com.example.mybible.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bible_entity")
public class BibleDataEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "doc")
    private String doc;

    @ColumnInfo(name = "chapter")
    private String chapter;

    @ColumnInfo(name = "verse")
    private String verse;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "active")
    private boolean active = false;

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

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
