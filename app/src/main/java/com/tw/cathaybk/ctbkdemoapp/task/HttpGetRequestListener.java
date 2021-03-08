package com.tw.cathaybk.ctbkdemoapp.task;

public interface HttpGetRequestListener {
    void onRequestFinish(String result);
    void onImgRequestFinish(String id, String result);
    void onRequestFail(String result);
}
