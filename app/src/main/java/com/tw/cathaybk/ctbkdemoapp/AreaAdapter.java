package com.tw.cathaybk.ctbkdemoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private Context context;
    private List<AreaData> areaList;

    AreaAdapter(Context context, List<AreaData> areaList) {
        this.context = context;
        this.areaList = areaList;
    }

    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_area, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AreaAdapter.ViewHolder holder, int position) {
        final AreaData areaData = areaList.get(position);
        final String areaName = areaData.getE_Name();
        final String img = areaData.getImage();

        //TODO set true image
      holder.image.setImageResource(R.mipmap.ic_launcher); //TODO get image
//        holder.image.setImageURI(Uri.parse(areadata.getE_Pic_URL()));
//        holder.image.setImageBitmap(
//                getImageBitmap(areadata.getE_Pic_URL())
//        );

        holder.areaName.setText(areaName);
        holder.areaDesc.setText(areaData.getE_Info());
        holder.areaMemo.setText(areaData.getE_Memo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("itemView onClick =", areaName);

                ArrayList<AreaData> data = new ArrayList<AreaData>();
                data.add(areaData);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("areaDataList", data);

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("areaDataBundle", bundle);

                //例外資料處理
                if(areaName.contains(context.getString(R.string.area_pangolin))){
                    intent.putExtra("areaName",context.getString(R.string.area_pangolin));
                } else {
                    intent.putExtra("areaName",areaName);
                }
                context.startActivity(intent);
                v.setClickable(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView areaName, areaDesc, areaMemo;
        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
            areaName = (TextView) itemView.findViewById(R.id.tv_area_name);
            areaDesc = (TextView) itemView.findViewById(R.id.tv_area_desc);
            areaMemo = (TextView) itemView.findViewById(R.id.tv_area_memo);
        }

    }
}
