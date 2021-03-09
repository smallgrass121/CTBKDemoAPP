package com.tw.cathaybk.ctbkdemoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {
    private Context context;
    private List<PlantData> plantDataList;

    PlantAdapter(Context context, List<PlantData> plantDataList) {
        this.context = context;
        this.plantDataList = plantDataList;
    }

    @Override
    public PlantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantAdapter.ViewHolder holder, int position) {
        final PlantData plantData = plantDataList.get(position);
        final String plantName = plantData.getF_Name_Ch();
        final String imgPath = plantData.getImage();

        if(null != imgPath && imgPath.length() > 0){
            File imgFile = new File(imgPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.image.setImageBitmap(myBitmap);
            }
        }

        holder.plantName.setText(plantName);
        holder.alsoknown.setText(plantData.getF_AlsoKnown());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("itemView onClick =", plantName);

                ArrayList<PlantData> data = new ArrayList<PlantData>();
                data.add(plantData);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("plantDataList", data);

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("plantDataBundle", bundle);

                context.startActivity(intent);
                v.setClickable(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView plantName, alsoknown;
        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image_plant);
            plantName = (TextView) itemView.findViewById(R.id.tv_plant_name);
            alsoknown = (TextView) itemView.findViewById(R.id.tv_also_known);
        }

    }
}
