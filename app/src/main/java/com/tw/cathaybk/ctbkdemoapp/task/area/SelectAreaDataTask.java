package com.tw.cathaybk.ctbkdemoapp.task.area;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.area.AreaDataDataBase;

import java.util.List;

public class SelectAreaDataTask extends AsyncTask<Void, Void, List<AreaData>> {

    private Context context;
    private SelectAreaDataListener listener;

    public SelectAreaDataTask(Context context, SelectAreaDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<AreaData> doInBackground(Void... voids){
        Log.i("SelectAreaDataTask doInBackground","start");

        List<AreaData> selectResult = null;
        try {
            selectResult = AreaDataDataBase.getInstance(context).getAreaDataDao().selectAll();
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("SelectAreaDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<AreaData> selectResult){
        Log.i("SelectAreaDataTask onPostExecute start", "");
        super.onPostExecute(selectResult);
        if(null != selectResult){
            Log.i("SelectAreaDataTask onPostExecute , selectResult=", selectResult.toString());
        }

        listener.onSelectAreaDataFinish(selectResult);

    }
}