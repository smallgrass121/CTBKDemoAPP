package com.tw.cathaybk.ctbkdemoapp.task.area;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;

import java.util.ArrayList;
import java.util.List;

public interface InsertAreaDataListener {
    void onInsertAreaDataFinish(List<AreaData> selectResult);
    void onInsertAreaDataFail(String result);
    void onImgUrlFind(String id, String url);
}
