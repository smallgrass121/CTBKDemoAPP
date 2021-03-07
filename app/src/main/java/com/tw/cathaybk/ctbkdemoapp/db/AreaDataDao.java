package com.tw.cathaybk.ctbkdemoapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AreaDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AreaData... data);

    @Update
    void update(AreaData... data);

    @Query("DELETE FROM areadata")
    void deleteAll();

    @Query("SELECT * FROM areadata")
    List<AreaData> selectAll();
}
