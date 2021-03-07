package com.tw.cathaybk.ctbkdemoapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.AreaDataDataBase;

import java.util.List;

public class InsertAreaDataTask extends AsyncTask<List<AreaData>, Void, List<AreaData>> {

    private Context context;
    private InsertAreaDataListener listener;

    public InsertAreaDataTask(Context context, InsertAreaDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<AreaData> doInBackground(List<AreaData>... lists){
        Log.i("InsertAreaDataTask doInBackground","start");

        List<AreaData> selectResult;
        List<AreaData> list = lists[0];

        try {
            for (int i=0; i<list.size(); i++) {
                AreaDataDataBase.getInstance(context).getAreaDataDao().insert(list.get(i));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            selectResult = AreaDataDataBase.getInstance(context).getAreaDataDao().selectAll();
        }

        Log.i("InsertAreaDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<AreaData> selectResult){
        Log.i("InsertAreaDataTask onPostExecute , selectResult=", selectResult.toString());

        super.onPostExecute(selectResult);
        listener.onInsertAreaDataFinish(selectResult);
    }
}