package com.tw.cathaybk.ctbkdemoapp.task.area;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;

import java.util.List;

public interface SelectAreaDataListener {
    void onSelectAreaDataFinish(List<AreaData> selectResult);
    void onSelectAreaDataFail(String result);
}
