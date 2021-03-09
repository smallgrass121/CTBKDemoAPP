package com.tw.cathaybk.ctbkdemoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;
import com.tw.cathaybk.ctbkdemoapp.task.DownloadFileListener;
import com.tw.cathaybk.ctbkdemoapp.task.DownloadFileTask;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestListener;
import com.tw.cathaybk.ctbkdemoapp.task.HttpGetRequestTask;
import com.tw.cathaybk.ctbkdemoapp.task.plant.InsertImgPlantDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.plant.InsertPlantDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.plant.InsertPlantDataTask;
import com.tw.cathaybk.ctbkdemoapp.task.plant.InsertPlantImgDataTask;
import com.tw.cathaybk.ctbkdemoapp.task.plant.SelectPlantDataListener;
import com.tw.cathaybk.ctbkdemoapp.task.plant.SelectPlantDataTask;
import com.tw.cathaybk.ctbkdemoapp.util.CSVFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlantListActivity extends Fragment
        implements HttpGetRequestListener, InsertPlantDataListener, SelectPlantDataListener, DownloadFileListener, InsertImgPlantDataListener {

    private Context context;

    private ImageView areaImage;
    private TextView areaDetail, areaInfo, arealink;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private String plantArea;

    private ProgressDialog progress = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_plant_list, container,false);
        this.context = this.getContext();

        ArrayList<AreaData> data =  getArguments().getParcelableArrayList("areaDataList");
        if(null == data || data.size() == 0 ) {
            showErrorDialog();
        }

        final AreaData areaData = data.get(0);
        if(null == areaData) {
            showErrorDialog();
        }

        plantArea = areaData.getE_Name();
        //例外資料處理
        if(plantArea.contains(context.getString(R.string.area_pangolin))){
            plantArea = context.getString(R.string.area_pangolin);
        }

        if(null == plantArea || plantArea.length() == 0 ) {
            showErrorDialog();
        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.detail_toolbar_plant);
        getActivity().setTitle(plantArea);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        areaImage = (ImageView) view.findViewById(R.id.iv_area_image);
        areaDetail = (TextView) view.findViewById(R.id.tv_area_detail);
        areaInfo = (TextView) view.findViewById(R.id.tv_area_info);
        arealink = (TextView) view.findViewById(R.id.tv_area_link);

        final String imgPath = areaData.getImage();
        if(null != imgPath && imgPath.length() > 0){
            File imgFile = new File(imgPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                areaImage.setImageBitmap(myBitmap);
            }
        }

        areaDetail.setText(areaData.getE_Info());

        StringBuilder areaInfoData = new StringBuilder(areaData.getE_Memo()).
                append("\n").append(areaData.getE_Category());
        areaInfo.setText(areaInfoData);

        arealink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(areaData.getE_URL()));
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.requestPlantAPI(plantArea,true);
        //TODO request all/part btn

        //this.checkAreaDataDB();

        return view;
    }

    private void checkPlantDataDB(){
        showLoading();
        new SelectPlantDataTask(context, this).execute(plantArea);
    }

    @Override
    public void onSelectPlantDataFinish(List<PlantData> selectResult) {
        Log.i("onSelectPlantDataFinish start", "");

        if(null == selectResult || selectResult.size() == 0) {
            Log.i("onSelectPlantDataFinish", "importCSV");
            this.importCSV();
        }else{
            Log.i("onSelectPlantDataFinish start, selectResult=", selectResult.toString());
            Log.i("onSelectPlantDataFinish", "setAdapter");
            mRecyclerView.setAdapter(new PlantAdapter(context, selectResult));
        }
    }

    @Override
    public void onRequestFinish(String result) {
        Log.i("onRequestFinish start, result=",result.toString());

        if(null != result){
            List <PlantData> plantDataList = null;
            //PlantData plantData = null;
            JSONObject mainObject = null;
            try {
                mainObject = new JSONObject(result.toString());
                JSONObject resultObj = mainObject.getJSONObject("result");
                JSONArray plantDataArray = resultObj.getJSONArray("results");

                plantDataList = new ArrayList <PlantData>();

                for (int i=0; i<plantDataArray.length(); i++) {
                    Log.i("onRequestFinish", plantDataArray.get(i).toString());

                    JSONObject itemObj =  new JSONObject(plantDataArray.get(i).toString());

                    //getString -> optString
                    PlantData plantData = new PlantData();
                    plantData.setF_Name_Latin(itemObj.optString("F_Name_Latin"));
                    plantData.setF_pdf02_ALT(itemObj.optString("F_pdf02_ALT"));
                    plantData.setF_Location(itemObj.optString("F_Location"));
                    plantData.setF_pdf01_ALT(itemObj.optString("F_pdf01_ALT"));
                    plantData.setF_Summary(itemObj.optString("F_Summary"));
                    plantData.setF_Pic01_URL(itemObj.optString("F_Pic01_URL"));
                    plantData.setF_pdf02_URL(itemObj.optString("F_pdf02_URL"));
                    plantData.setF_Pic02_URL(itemObj.optString("F_Pic02_URL"));

                    String tempName = itemObj.optString("\uFEFFF_Name_Ch");
                    if(tempName.equals("")){
                        tempName = itemObj.optString("F_Name_Ch");

                    }
                    plantData.setF_Name_Ch(tempName);

                    plantData.setF_Keywords(itemObj.optString("F_Keywords"));
                    plantData.setF_Code(itemObj.optString("F_Code"));
                    plantData.setF_Geo(itemObj.optString("F_Geo"));
                    plantData.setF_Pic03_URL(itemObj.optString("F_Pic03_URL"));
                    plantData.setF_Voice01_ALT(itemObj.optString("F_Voice01_ALT"));
                    plantData.setF_AlsoKnown(itemObj.optString("F_AlsoKnown"));
                    plantData.setF_Voice02_ALT(itemObj.optString("F_Voice02_ALT"));
                    plantData.setF_Pic04_ALT(itemObj.optString("F_Pic04_ALT"));
                    plantData.setF_Name_En(itemObj.optString("F_Name_En"));
                    plantData.setF_Brief(itemObj.optString("F_Brief"));
                    plantData.setF_Pic04_URL(itemObj.optString("F_Pic04_URL"));
                    plantData.setF_Voice01_URL(itemObj.optString("F_Voice01_URL"));
                    plantData.setF_Feature(itemObj.optString("F_Feature"));
                    plantData.setF_Pic02_ALT(itemObj.optString("F_Pic02_ALT"));
                    plantData.setF_Family(itemObj.optString("F_Family"));
                    plantData.setF_Voice03_ALT(itemObj.optString("tF_Voice03_ALT"));
                    plantData.setF_Voice02_URL(itemObj.optString("F_Voice02_URL"));
                    plantData.setF_Pic03_ALT(itemObj.optString("F_Pic03_ALT"));
                    plantData.setF_Pic01_ALT(itemObj.optString("F_Pic01_ALT"));
                    plantData.setF_CID(itemObj.optString("F_CID"));
                    plantData.setF_pdf01_URL(itemObj.optString("F_pdf01_URL"));
                    plantData.setF_Vedio_URL(itemObj.optString("F_Vedio_URL"));
                    plantData.setF_Genus(itemObj.optString("F_Genus"));
                    plantData.setF_FunctionAndApplication(itemObj.optString("setF_Function&Application"));
                    plantData.setF_Voice03_URL(itemObj.optString("F_Voice03_URL"));
                    plantData.setF_Update(itemObj.optString("F_Update"));

                    plantDataList.add(plantData);
                }
                if(null!= plantDataList && plantDataList.size()>0){
                    new InsertPlantDataTask(context, this).execute(plantDataList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onInsertPlantDataFinish(List<PlantData> selectResult) {
        cancelLoading();
        Log.i("onInsertPlantDataFinish start, selectResult=", selectResult.toString());
        mRecyclerView.setAdapter(new PlantAdapter(context, selectResult));
    }

    @Override
    public void onRequestFail(String result) {
        cancelLoading();
        Log.i("onRequestFail start, result=", result.toString());
        this.checkPlantDataDB();
    }

    @Override
    public void onInsertPlantDataFail(String result) {
        cancelLoading();
        Log.i("onInsertPlantDataFail start, result=", result.toString());
    }


    @Override
    public void onSelectPlantDataFail(String result) {
        cancelLoading();
        Log.i("onSelectPlantDataFail start, result=", result.toString());
    }

    private void importCSV(){
        showLoading();
        Log.i("importCSV start", "");

        InputStream inputStream = getResources().openRawResource(R.raw.plantdata_1090818);
        CSVFile csvFile = new CSVFile(inputStream);
        List csvDataList = csvFile.read();

        List<PlantData> list = new ArrayList();
        for (int i=0; i<csvDataList.size(); i++) {
            //skip title line
            if(i==0) {continue;}

            String item[] = (String[]) csvDataList.get(i);
            PlantData plantData = new PlantData();

            plantData.setF_Name_Ch(item[0]);
            plantData.setF_Summary(item[1]);
            plantData.setF_Keywords(item[2]);
            plantData.setF_AlsoKnown(item[3]);
            plantData.setF_Geo(item[4]);
            plantData.setF_Location(item[5]);
            plantData.setF_Name_En(item[6]);
            plantData.setF_Name_Latin(item[7]);
            plantData.setF_Family(item[8]);
            plantData.setF_Genus(item[9]);
            plantData.setF_Brief(item[10]);
            plantData.setF_Feature(item[11]);
            plantData.setF_FunctionAndApplication(item[11]);
            plantData.setF_Code(item[12]);
            plantData.setF_Pic01_ALT(item[13]);
            plantData.setF_Pic01_URL(item[14]);
            plantData.setF_Pic02_ALT(item[15]);
            plantData.setF_Pic02_URL(item[16]);
            plantData.setF_Pic03_ALT(item[17]);
            plantData.setF_Pic03_URL(item[18]);
            plantData.setF_Pic04_ALT(item[19]);
            plantData.setF_Pic04_URL(item[20]);
            plantData.setF_pdf01_ALT(item[21]);
            plantData.setF_pdf01_URL(item[22]);
            plantData.setF_pdf02_ALT(item[23]);
            plantData.setF_pdf02_URL(item[24]);
            plantData.setF_Voice01_ALT(item[25]);
            plantData.setF_Voice01_URL(item[26]);
            plantData.setF_Voice02_ALT(item[27]);
            plantData.setF_Voice02_URL(item[28]);
            plantData.setF_Voice03_ALT(item[29]);
            plantData.setF_Voice03_URL(item[30]);
            plantData.setF_Vedio_URL(item[31]);
            plantData.setF_Update(item[32]);
            plantData.setF_CID(item[33]);

            list.add(plantData);
        }
        Log.i("importCSV end", "");
        if(list.size()>0){
            new InsertPlantDataTask(context, this).execute(list);
        }
    }

    @Override
    public void onImgUrlFind(String id, String url) {
        Log.i("onImgUrlFind start:", "id = "+id+ " url = "+url);
        new DownloadFileTask(context, this).execute("PlantList", id, url);
    }

    private void requestPlantAPI(String plantArea, boolean requestAll){
        StringBuilder sbUrl = new StringBuilder(getString(R.string.url_plant_data_api)).append("&q=").append(plantArea);
        if(!requestAll){
            sbUrl.append("&limit=").append("10");
        }
        new HttpGetRequestTask(this).execute(sbUrl.toString());
    }

    @Override
    public void onDownloadFinish(String name, String path) {
        Log.i("onImgRequestFinish start, id=", name.toString());
        Log.i("onImgRequestFinish start, path=", path);
        new InsertPlantImgDataTask(context, this).execute(name, path);
    }

    @Override
    public void onInsertImgDataFinish(List<PlantData> selectResult) {
        mRecyclerView.setAdapter(new PlantAdapter(context, selectResult));
//        mRecyclerView.notifyAll();
        cancelLoading();
    }

    @Override
    public void onInsertImgDataFail(String result) {
        cancelLoading();
    }

    @Override
    public void onDownloadFail(String result) {
        cancelLoading();
    }

    private void showLoading() {
//        if(null == progress){
//            progress = new ProgressDialog(context);
//        }
//
//        if(!progress.isShowing()){
//            progress.setTitle("Loading");
//            progress.setCancelable(false);
//            progress.show();
//        }
    }

    private void cancelLoading() {
//        if(null != progress && progress.isShowing()){
//            progress.dismiss();
//        }
    }

    public void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        android.content.DialogInterface.OnClickListener ocListener = new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().onBackPressed();
            }
        };

        builder.setTitle("Error");
        builder.setMessage("發生問題，請稍後再試，謝謝您。");
        builder.setPositiveButton("OK", ocListener);
        builder.setCancelable(false);
        builder.show();
    }
}
