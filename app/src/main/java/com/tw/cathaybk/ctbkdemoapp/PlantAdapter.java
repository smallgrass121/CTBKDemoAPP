package com.tw.cathaybk.ctbkdemoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

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
        final PlantData plantdata = plantDataList.get(position);
        final String plantName = plantdata.getF_Name_Ch();
        final String img = plantdata.getImage();

        //TODO set true image
      holder.image.setImageResource(R.mipmap.ic_launcher); //TODO get image
//        holder.image.setImageURI(Uri.parse(areadata.getE_Pic_URL()));
//        holder.image.setImageBitmap(
//                getImageBitmap(areadata.getE_Pic_URL())
//        );

        holder.plantName.setText(plantName);
        holder.alsoknown.setText(plantdata.getF_AlsoKnown());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Log.i("itemView onClick =", plantName);
            //TODO pass plantdata
            }
        });
//        this.notifyDataSetChanged();
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
