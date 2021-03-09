package com.tw.cathaybk.ctbkdemoapp.task;

public interface HttpGetRequestListener {
    void onRequestFinish(String result);
    void onRequestFail(String result);
}
