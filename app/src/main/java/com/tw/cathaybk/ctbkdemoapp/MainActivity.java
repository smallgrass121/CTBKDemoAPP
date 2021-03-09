package com.tw.cathaybk.ctbkdemoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tw.cathaybk.ctbkdemoapp.db.area.AreaData;
import com.tw.cathaybk.ctbkdemoapp.db.plant.PlantData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private AreaListActivity areaListActivity = new AreaListActivity();
    private PlantListActivity plantListActivity = new PlantListActivity();
    private PlantDetailActivity plantDetailActivity = new PlantDetailActivity();

    private static ArrayList<AreaData> areaListData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        Bundle bundle = null;

        ArrayList<AreaData> tmpAreaListData = null;
        ArrayList<PlantData> tmpPlantListData = null;

        if(null != extras) {
            bundle = extras.getParcelable("areaDataBundle");
            if(null != bundle){
                tmpAreaListData = bundle.getParcelableArrayList("areaDataList");
            }else{
                bundle = extras.getParcelable("plantDataBundle");
                tmpPlantListData = bundle.getParcelableArrayList("plantDataList");
            }
        }

        if( (null == tmpAreaListData || tmpAreaListData.size() == 0)
                && (null == tmpPlantListData || tmpPlantListData.size() == 0))
        {
            showAreaList();
        } else if(null != tmpAreaListData && tmpAreaListData.size() > 0){
            areaListData = tmpAreaListData;
            showPlantList(tmpAreaListData);
        } else if((null != tmpPlantListData && tmpPlantListData.size() > 0)){
            showPlantDetail(tmpPlantListData);
        }
    }

    private void showAreaList(){
        if(!areaListActivity.isVisible()){
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, areaListActivity, "AreaList")
                    .setReorderingAllowed(true)
                    .show(areaListActivity)
                    .hide(plantListActivity)
                    .hide(plantDetailActivity)
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
                    .hide(plantDetailActivity)
                    .addToBackStack(areaListActivity.getClass().getName())
                    .commit();

        }
    }

    private void showPlantDetail(ArrayList<PlantData> data){
        if(!plantListActivity.isVisible()){

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("plantDataList", data);
            plantDetailActivity.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, plantDetailActivity, "PlantDetail")
                    .setReorderingAllowed(true)
                    .show(plantDetailActivity)
                    .hide(plantListActivity)
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
        else if(plantDetailActivity.isVisible()){
            this.showPlantList(areaListData);
        }
    }
}
