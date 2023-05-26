package com.example.mybible.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BibleDataDao {
    @Insert(onConflict = REPLACE)
    void insert(BibleDataEntity bibleData);

    @Query("SELECT * FROM bible_entity WHERE doc = :doc AND chapter = :chapter")
    List<BibleDataEntity> getBibleEntity(String doc, String chapter);

    @Query("UPDATE bible_entity SET active = :active WHERE id = :id")
    void update(boolean active, int id);
}
