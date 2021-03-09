package com.tw.cathaybk.ctbkdemoapp.task.plant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantDataDataBase;

import java.util.List;

public class InsertPlantImgDataTask extends AsyncTask<String, Void, List<PlantData>> {

    private Context context;
    private InsertImgPlantDataListener listener;

    public InsertPlantImgDataTask(Context context, InsertImgPlantDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<PlantData> doInBackground(String... para){
        Log.i("InsertImgDataTask doInBackground","start");

        List<PlantData> selectResult;
        String imgName = para[0];
        String imgData = para[1];

        if(null == imgName || null == imgData){
            listener.onInsertImgDataFail("parameter error: "
                    + "imgId = " + imgName
                    +"\n imgData = " + imgData);
        }

        try {
            PlantDataDataBase.getInstance(context).getPlantDataDao().updateImageByName(imgName, imgData);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            selectResult = PlantDataDataBase.getInstance(context).getPlantDataDao().selectAll();
        }

        Log.i("InsertImgDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<PlantData> selectResult){
        Log.i("InsertImgDataTask onPostExecute , selectResult=", selectResult.toString());

        super.onPostExecute(selectResult);
        listener.onInsertImgDataFinish(selectResult);
    }
}