package com.example.mybible.api;


import com.example.mybible.db.BibleDataEntity;
import com.google.gson.annotations.SerializedName;

public class BibleVO {
    @SerializedName("chapter")
    String chapter = "";

    @SerializedName("verse")
    String verse = "";

    @SerializedName("message")
    String message = "";

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

    public BibleDataEntity setEntity(String fixDoc){
        BibleDataEntity entity = new BibleDataEntity();
        entity.setDoc(fixDoc);
        entity.setChapter(this.chapter);
        entity.setVerse(this.verse);
        entity.setMessage(this.message);

        return entity;
    }
}
