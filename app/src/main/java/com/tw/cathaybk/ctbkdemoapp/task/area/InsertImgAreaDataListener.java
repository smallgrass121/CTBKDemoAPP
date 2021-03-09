package com.tw.cathaybk.ctbkdemoapp.task.area;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;

import java.util.List;

public interface InsertImgAreaDataListener {
    void onInsertImgDataFinish(List<AreaData> selectResult);
    void onInsertImgDataFail(String result);
}
