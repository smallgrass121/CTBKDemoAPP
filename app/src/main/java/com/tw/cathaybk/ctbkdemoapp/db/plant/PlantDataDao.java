package com.tw.cathaybk.ctbkdemoapp.db.plant;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlantDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlantData... data);

    @Update
    void update(PlantData... data);

    @Query("UPDATE plantdata SET image = :image WHERE F_Name_Ch = :name")
    void updateImageByName(String name, String image);

    @Query("DELETE FROM plantdata")
    void deleteAll();

    @Query("SELECT * FROM plantdata")
    List<PlantData> selectAll();

    @Query("SELECT * FROM plantdata where F_Location LIKE '%' || :area || '%'")
    List<PlantData> getALLByArea(String area);

    @Query("SELECT * FROM plantdata where F_Name_Ch = :name")
    List<PlantData> getALLByName(String name);

    @Query("SELECT image FROM plantdata where F_Name_Ch = :name")
    String getImageByName(String name);
}