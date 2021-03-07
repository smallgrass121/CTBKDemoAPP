package com.tw.cathaybk.ctbkdemoapp.task;

import com.tw.cathaybk.ctbkdemoapp.db.AreaData;

import java.util.List;

public interface SelectAreaDataListener {
    void onSelectAreaDataFinish(List<AreaData> selectResult);
    void onSelectAreaDataFail(String result);
}
