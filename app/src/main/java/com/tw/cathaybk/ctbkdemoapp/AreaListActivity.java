package com.tw.cathaybk.ctbkdemoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.task.DownloadFileListener;
import com.tw.cathaybk.ctbkdemoapp.task.DownloadFileTask;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestListener;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestTask;
import com.tw.cathaybk.ctbkdemoapp.task.area.InsertAreaDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.area.InsertAreaDataTask;
import com.tw.cathaybk.ctbkdemoapp.task.area.InsertAreaImgDataTask;
import com.tw.cathaybk.ctbkdemoapp.task.area.InsertImgAreaDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.area.SelectAreaDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.area.SelectAreaDataTask;
import com.tw.cathaybk.ctbkdemoapp.util.CSVFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AreaListActivity extends Fragment
        implements HttpGetRequestListener, InsertAreaDataListener, SelectAreaDataListener, InsertImgAreaDataListener, DownloadFileListener {

    private Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private ProgressDialog progress = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = this.getContext();
        showloading();
        View view = inflater.inflate(R.layout.activity_area_list, container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(getString(R.string.app_name));
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.app_name);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        /***
         * step 1.  一概透過api取得資料
         * step 2.  比對response是否與儲存的相同
         *      2.1 不同則重新下載匯入(step 3)
         *      2.2 相同則直接使用ROOM資料(step 5)
         * step 3.  HttpGetRequestTask Call API取得最新版本
         * step 4.  InsertAreaDataTask insert 最新版本資料
         *      4.1 下載圖片, 透過 InsertAreaImgDataTask update 儲存圖片, 並更新畫面
         * step 5.  載入ROOM資料顯示
         */

        this.requestAreaAPI(getString(R.string.url_area_data_api));
        //this.checkAreaDataDB();

        return view;
    }

    private void checkAreaDataDB(){
        showloading();
        new SelectAreaDataTask(context, this).execute();
    }

    @Override
    public void onSelectAreaDataFinish(List<AreaData> selectResult) {
        cancelloading();
        Log.i("onSelectAreaDataFinish start", "");

        if(null == selectResult || selectResult.size() == 0) {
            Log.i("onSelectAreaDataFinish", "importCSV");
            this.importCSV();
        }else{
            Log.i("onSelectAreaDataFinish start, selectResult=", selectResult.toString());
            Log.i("onSelectAreaDataFinish", "setAdapter");
            mRecyclerView.setAdapter(new AreaAdapter(context, selectResult));
        }
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

                for (int i=0; i<areaDataArray.length(); i++) {
                    Log.i("onRequestFinish", areaDataArray.get(i).toString());

                    JSONObject itemObj =  new JSONObject(areaDataArray.get(i).toString());
                    areaData = new AreaData();
                    areaData.setE_no(itemObj.getString("E_no"));
                    areaData.setE_Category(itemObj.getString("E_Category"));
                    areaData.setE_Name(itemObj.getString("E_Name"));
                    areaData.setE_Pic_URL(itemObj.getString("E_Pic_URL"));
                    areaData.setE_Info(itemObj.getString("E_Info"));
                    areaData.setE_Memo(itemObj.getString("E_Memo"));
                    areaData.setE_Geo(itemObj.getString("E_Geo"));
                    areaData.setE_URL(itemObj.getString("E_URL"));

                    areaDataList.add(areaData);
                }
                if(null!= areaDataList && areaDataList.size()>0){
                    new InsertAreaDataTask(context, this).execute(areaDataList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            this.importCSV();
        }
        cancelloading();
    }

    @Override
    public void onInsertAreaDataFinish(List<AreaData> selectResult) {
        showloading();
        Log.i("onInsertAreaDataFinish start, selectResult=", selectResult.toString());
        mRecyclerView.setAdapter(new AreaAdapter(context, selectResult));
    }

    @Override
    public void onDownloadFinish(String id, String path) {
        cancelloading();
        showloading();
        Log.i("onImgRequestFinish start, id=", id.toString());
        Log.i("onImgRequestFinish start, path=", path);
        new InsertAreaImgDataTask(context, this).execute(id, path);
    }

    @Override
    public void onRequestFail(String result) {
        cancelloading();
        Log.i("onRequestFail start, result=", result.toString());
        this.checkAreaDataDB();
    }

    @Override
    public void onInsertAreaDataFail(String result) {
        cancelloading();
        Log.i("onInsertAreaDataFail start, result=", result.toString());
    }


    @Override
    public void onSelectAreaDataFail(String result) {
        cancelloading();
        Log.i("onSelectAreaDataFail start, result=", result.toString());
    }

    private void importCSV(){
        showloading();
        Log.i("importCSV start", "");

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
        Log.i("importCSV end", "");
        if(list.size()>0){
            new InsertAreaDataTask(context, this).execute(list);
        }
    }

    @Override
    public void onImgUrlFind(String id, String url) {
        showloading();
        Log.i("onImgUrlFind start:", "id = "+id+ " url = "+url);
        new DownloadFileTask(context, this).execute("AreaList", id, url);
    }

    private void requestAreaAPI(String url){
        showloading();
        new HttpGetRequestTask(this).execute(url);
    }

    @Override
    public void onInsertImgDataFinish(List<AreaData> selectResult) {
        mRecyclerView.setAdapter(new AreaAdapter(context, selectResult));
        cancelloading();
    }

    @Override
    public void onInsertImgDataFail(String result) {
        cancelloading();
    }

    @Override
    public void onDownloadFail(String result) {
        cancelloading();
    }

    private void showloading() {
        if(null == progress){
            progress = new ProgressDialog(context);
        }

        if(!progress.isShowing()){
            progress.setTitle("Loading");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
        }
    }

    private void cancelloading() {
        if(null != progress && progress.isShowing()){
            progress.dismiss();
        }
    }
}
