package com.tw.cathaybk.ctbkdemoapp.task.plant;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.util.List;

public interface InsertPlantDataListener {
    void onInsertPlantDataFinish(List<PlantData> selectResult);
    void onInsertPlantDataFail(String result);
    void onImgUrlFind(String id, String url);
}
