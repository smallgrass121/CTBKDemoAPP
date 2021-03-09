package com.tw.cathaybk.ctbkdemoapp.task.area;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.area.AreaDataDataBase;

import java.util.List;

public class InsertAreaImgDataTask extends AsyncTask<String, Void, List<AreaData>> {

    private Context context;
    private InsertImgAreaDataListener listener;

    public InsertAreaImgDataTask(Context context, InsertImgAreaDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<AreaData> doInBackground(String... para){
        Log.i("InsertImgDataTask doInBackground","start");

        List<AreaData> selectResult;
        String imgId = para[0];
        String imgData = para[1];

        if(null == imgId || null == imgData){
            listener.onInsertImgDataFail("parameter error: "
                    + "imgId = " + imgId
                    +"\n imgData = " + imgData);
        }

        try {
            AreaDataDataBase.getInstance(context).getAreaDataDao().updateImageById(imgId, imgData);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            selectResult = AreaDataDataBase.getInstance(context).getAreaDataDao().selectAll();
        }

        Log.i("InsertImgDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<AreaData> selectResult){
        Log.i("InsertImgDataTask onPostExecute , selectResult=", selectResult.toString());

        super.onPostExecute(selectResult);
        listener.onInsertImgDataFinish(selectResult);
    }
}