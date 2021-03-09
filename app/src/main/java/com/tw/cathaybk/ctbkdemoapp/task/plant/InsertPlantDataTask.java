package com.tw.cathaybk.ctbkdemoapp.task.plant;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantDataDataBase;

import java.util.List;

public class InsertPlantDataTask extends AsyncTask<List<PlantData>, Void, List<PlantData>> {

    private Context context;
    private InsertPlantDataListener listener;

    public InsertPlantDataTask(Context context, InsertPlantDataListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<PlantData> doInBackground(List<PlantData>... lists){
        Log.i("InsertPlantDataTask doInBackground","start");

        List<PlantData> selectResult;
        List<PlantData> list = lists[0];

        try {
            for (int i=0; i<list.size(); i++) {
                PlantDataDataBase.getInstance(context).getPlantDataDao().insert(list.get(i));

                final String id = list.get(i).getF_Name_Ch();
                final String url = list.get(i).getF_Pic01_URL();

                if(null != id && null != url && url.startsWith("http")){
                    listener.onImgUrlFind(id, url);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            selectResult = PlantDataDataBase.getInstance(context).getPlantDataDao().selectAll();
        }

        Log.i("InsertPlantDataTask doInBackground","finish");

        return selectResult;
    }

    protected void onPostExecute(List<PlantData> selectResult){
        Log.i("InsertPlantDataTask onPostExecute , selectResult=", selectResult.toString());

        super.onPostExecute(selectResult);
        listener.onInsertPlantDataFinish(selectResult);
    }
}