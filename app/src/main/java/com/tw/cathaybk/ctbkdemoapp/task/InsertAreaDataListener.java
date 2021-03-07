package com.tw.cathaybk.ctbkdemoapp.task;

import com.tw.cathaybk.ctbkdemoapp.db.AreaData;

import java.util.List;

public interface InsertAreaDataListener {
    void onInsertAreaDataFinish(List<AreaData> selectResult);
    void onInsertAreaDataFail(String result);
}
