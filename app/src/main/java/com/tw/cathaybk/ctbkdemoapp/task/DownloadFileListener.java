package com.tw.cathaybk.ctbkdemoapp.task;

public interface DownloadFileListener {
    void onDownloadFinish(String id, String path);
    void onDownloadFail(String result);
}
