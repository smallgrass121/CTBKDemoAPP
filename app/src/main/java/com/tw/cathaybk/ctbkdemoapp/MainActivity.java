package com.tw.cathaybk.ctbkdemoapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private AreaListActivity areaListActivity = new AreaListActivity();
    private PlantListActivity plantListActivity = new PlantListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String areaName = intent.getStringExtra("areaName");

        if(null == areaName || areaName.length() == 0) {
            showAreaList();
        }else{
            showPlantList(areaName);
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

    private void showPlantList(String areaName){
        if(!plantListActivity.isVisible()){

            Bundle arguments = new Bundle();
            arguments.putString( "areaName" , areaName);
            plantListActivity.setArguments(arguments);

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
