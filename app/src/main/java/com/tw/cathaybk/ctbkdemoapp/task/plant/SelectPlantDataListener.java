package com.tw.cathaybk.ctbkdemoapp.task.plant;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.util.List;

public interface SelectPlantDataListener {
    void onSelectPlantDataFinish(List<PlantData> selectResult);
    void onSelectPlantDataFail(String result);
}
