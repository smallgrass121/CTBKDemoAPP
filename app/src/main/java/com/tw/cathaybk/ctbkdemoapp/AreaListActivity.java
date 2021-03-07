package com.tw.cathaybk.ctbkdemoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.tw.cathaybk.ctbkdemoapp.db.AreaData;
import com.tw.cathaybk.ctbkdemoapp.dummy.DummyContent;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestListener;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestTask;
import com.tw.cathaybk.ctbkdemoapp.task.InsertAreaDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.InsertAreaDataTask;
import com.tw.cathaybk.ctbkdemoapp.task.SelectAreaDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.SelectAreaDataTask;
import com.tw.cathaybk.ctbkdemoapp.util.CSVFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AreaListActivity extends AppCompatActivity
        implements HttpGetRequestListener, InsertAreaDataListener, SelectAreaDataListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        this.requestAreaAPI();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    @Override
    public void onRequestFinish(String result) {
        Log.i("onRequestFinish start, result=",result.toString());

        if(null != result){
            List <AreaData> areaDataList = null;
            AreaData areaData = null;
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(result.toString());
                JSONObject resultObj = mainObject.getJSONObject("result");
                JSONArray areaDataArray = resultObj.getJSONArray("results");

                areaDataList = new ArrayList <AreaData>();
                areaData = new AreaData();

                for (int i=0; i<areaDataArray.length(); i++) {
                    Log.i("onInsertAreaDataFinish", areaDataArray.get(i).toString());

                    JSONObject itemObj =  new JSONObject(areaDataArray.get(i).toString());

                    AreaData data = new AreaData();
                    data.setE_no(itemObj.getString("E_no"));
                    data.setE_Category(itemObj.getString("E_Category"));
                    data.setE_Name(itemObj.getString("E_Name"));
                    data.setE_Pic_URL(itemObj.getString("E_Pic_URL"));
                    data.setE_Info(itemObj.getString("E_Info"));
                    data.setE_Memo(itemObj.getString("E_Memo"));
                    data.setE_Geo(itemObj.getString("E_Geo"));
                    data.setE_URL(itemObj.getString("E_URL"));

                    areaDataList.add(data);
                }
                if(null!= areaDataList && areaDataList.size()>0){
                    new InsertAreaDataTask(getBaseContext(), this).execute(areaDataList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

        }
    }

    @Override
    public void onRequestFail(String result) {
        this.checkAreaDataDB();
    }

    @Override
    public void onInsertAreaDataFinish(List<AreaData> selectResult) {

    }

    @Override
    public void onInsertAreaDataFail(String result) {

    }

    @Override
    public void onSelectAreaDataFinish(List<AreaData> selectResult) {
        if(null == selectResult || selectResult.size() > 0){
            this.importCSV();
        }
    }

    @Override
    public void onSelectAreaDataFail(String result) {

    }

    private void checkAreaDataDB(){
        new SelectAreaDataTask(getBaseContext(), this).execute();
    }

    private void importCSV(){
        InputStream inputStream = getResources().openRawResource(R.raw.areadata_20200206);
        CSVFile csvFile = new CSVFile(inputStream);
        List csvDataList = csvFile.read();

        List<AreaData> list = new ArrayList();
        for (int i=0; i<csvDataList.size(); i++) {
            //skip title line
            if(i==0) {continue;}

            String item[] = (String[]) csvDataList.get(i);
            AreaData data = new AreaData();
            data.setE_no(item[0]);
            data.setE_Category(item[1]);
            data.setE_Name(item[2]);
            data.setE_Pic_URL(item[2]);
            data.setE_Info(item[4]);
            data.setE_Memo(item[5]);
            data.setE_Geo(item[6]);
            data.setE_URL(item[7]);

            list.add(data);
        }
        if(list.size()>0){
            new InsertAreaDataTask(getBaseContext(), this).execute(list);
        }
    }

    private void requestAreaAPI(){
        new HttpGetRequestTask(this).execute(getString(R.string.url_area_data_api));
    }
}
