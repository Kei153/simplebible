package com.example.mybible.db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MemoDataDao {

    @Insert(onConflict = REPLACE)
    void insert(MemoDataEntity memoDataEntity);

   @Query("SELECT * FROM memo_entity WHERE doc = :doc AND chapter = :chapter")
    MemoDataEntity getMemoData(String doc, String chapter);

   @Query("UPDATE memo_entity SET memo = :memo WHERE id = :id")
    void update(String memo, int id);
}
