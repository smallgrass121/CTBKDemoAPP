package com.tw.cathaybk.ctbkdemoapp.task.plant;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.util.List;

public interface InsertImgPlantDataListener {
    void onInsertImgDataFinish(List<PlantData> selectResult);
    void onInsertImgDataFail(String result);
}
