package com.tw.cathaybk.ctbkdemoapp.task.plant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantDataDataBase;

import java.util.List;

public class SelectPlantDataTask extends AsyncTask<Void, Void, List<PlantData>> {

    private Context context;
    private SelectPlantDataListener listener;

    public SelectPlantDataTask(Context context, SelectPlantDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<PlantData> doInBackground(Void... voids){
        Log.i("SelectPlantDataTask doInBackground","start");

        List<PlantData> selectResult = null;
        try {
            selectResult = PlantDataDataBase.getInstance(context).getPlantDataDao().selectAll();
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("SelectPlantDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<PlantData> selectResult){
        Log.i("SelectPlantDataTask onPostExecute start", "");
        super.onPostExecute(selectResult);
        if(null != selectResult){
            Log.i("SelectPlantDataTask onPostExecute , selectResult=", selectResult.toString());
        }

        listener.onSelectPlantDataFinish(selectResult);

    }
}