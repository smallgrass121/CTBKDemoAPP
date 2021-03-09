package com.tw.cathaybk.ctbkdemoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.io.File;
import java.util.ArrayList;

public class PlantDetailActivity extends Fragment {

    private Context context;

    private ImageView plantImage;
    private TextView plantNameInfo, plantAlsoknown, plantBrief, plantFeature, plantFuncApp, plantUpdate;

    private String plantName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_plant_detail, container,false);
        this.context = this.getContext();

        ArrayList<PlantData> data =  getArguments().getParcelableArrayList("plantDataList");
        if(null == data || data.size() == 0 ) {
            //TODO show error
        }

        final PlantData plantData = data.get(0);
        if(null == plantData) {
            //TODO show error
        }

        plantName = plantData.getF_Name_Ch();
        if(null == plantName || plantName.length() == 0 ) {
            //TODO show error
        }

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.detail_toolbar_plant_detail);
        getActivity().setTitle(plantName);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        plantImage = (ImageView) view.findViewById(R.id.iv_plant_image);
        plantNameInfo = (TextView) view.findViewById(R.id.tv_plant_name);
        plantAlsoknown = (TextView) view.findViewById(R.id.tv_plant_also_known);
        plantBrief = (TextView) view.findViewById(R.id.tv_plant_brief);
        plantFeature = (TextView) view.findViewById(R.id.tv_plant_feature);
        plantFuncApp = (TextView) view.findViewById(R.id.tv_plant_func_app);
        plantUpdate = (TextView) view.findViewById(R.id.tv_plant_update);

        final String imgPath = plantData.getImage();
        if(null != imgPath && imgPath.length() > 0){
            File imgFile = new File(imgPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                plantImage.setImageBitmap(myBitmap);
            }
        }

        plantNameInfo.setText(new StringBuilder(plantData.getF_Name_Ch()).append("\n").append(plantData.getF_Name_En()));
        plantAlsoknown.setText(new StringBuilder("別名").append("\n").append(plantData.getF_AlsoKnown()));
        plantBrief.setText(new StringBuilder("簡介").append("\n").append(plantData.getF_Brief()));
        plantFeature.setText(new StringBuilder("辨認方式").append("\n").append(plantData.getF_Feature()));
        plantFuncApp.setText(new StringBuilder("功能性").append("\n").append(plantData.getF_FunctionAndApplication()));
        plantUpdate.setText(new StringBuilder("最後更新").append("\t").append(plantData.getF_Update()));

        return view;
    }
}
