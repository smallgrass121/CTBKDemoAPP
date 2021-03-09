package com.tw.cathaybk.ctbkdemoapp.task.plant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantDataDataBase;

import java.util.List;

public class SelectPlantDataTask extends AsyncTask<String, Void, List<PlantData>> {

    private Context context;
    private SelectPlantDataListener listener;

    public SelectPlantDataTask(Context context, SelectPlantDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<PlantData> doInBackground(String... para){
        Log.i("SelectPlantDataTask doInBackground","start");

        String plantArea = para[0];
        if(null == plantArea || plantArea.length() == 0){
            listener.onSelectPlantDataFail("plantArea is empty");
        }

        List<PlantData> selectResult = null;
        try {
            selectResult = PlantDataDataBase.getInstance(context).getPlantDataDao().selectALLByName(plantArea);
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