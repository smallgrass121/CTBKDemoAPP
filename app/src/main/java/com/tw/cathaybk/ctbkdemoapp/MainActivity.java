package com.tw.cathaybk.ctbkdemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private AreaListActivity areaListActivity = new AreaListActivity();
    private PlantListActivity plantListActivity = new PlantListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        Bundle bundle = null;
        ArrayList<AreaData> data = null;
        if(null != extras) {
            bundle = extras.getParcelable("areaDataBundle");
            data = bundle.getParcelableArrayList("areaDataList");
        }

        if(null == data || data.size() == 0) {
            showAreaList();
        }else{
            showPlantList(data);
        }
    }

    private void showAreaList(){
        if(!areaListActivity.isVisible()){
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, areaListActivity, "AreaList")
                    .setReorderingAllowed(true)
                    .show(areaListActivity)
                    .hide(plantListActivity)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showPlantList(ArrayList<AreaData> data){
        if(!plantListActivity.isVisible()){

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("areaDataList", data);
            plantListActivity.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, plantListActivity, "PlantList")
                    .setReorderingAllowed(true)
                    .show(plantListActivity)
                    .hide(areaListActivity)
                    .addToBackStack(areaListActivity.getClass().getName())
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("onOptionsItemSelected, id=",item.getItemId()+"");
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(areaListActivity.isVisible()){

        }else if(plantListActivity.isVisible()){
            this.showAreaList();
        }
//        else if(){
//
//        }
    }
}
