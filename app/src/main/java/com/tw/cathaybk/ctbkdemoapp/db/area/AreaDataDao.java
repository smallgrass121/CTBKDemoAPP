package com.tw.cathaybk.ctbkdemoapp.db.area;

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

    @Query("UPDATE areadata SET image = :image WHERE E_no = :id")
    void updateImageById(String id, String image);

    @Query("DELETE FROM areadata")
    void deleteAll();

    @Query("SELECT * FROM areadata")
    List<AreaData> selectAll();

    @Query("SELECT image FROM areadata where E_no = :id")
    String getImageById(int id);
}
