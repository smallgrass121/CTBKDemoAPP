package com.tw.cathaybk.ctbkdemoapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private AreaListActivity areaListActivity = new AreaListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        fragmentTransaction.add(areaListActivity,"AreaList");
//        fragmentTransaction.show(areaListActivity);


        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, areaListActivity, "AreaList")
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }
}
