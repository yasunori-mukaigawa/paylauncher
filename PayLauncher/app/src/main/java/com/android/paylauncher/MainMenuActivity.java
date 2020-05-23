package com.android.paylauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.paylauncher.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainMenuActivity extends AppCompatActivity {

    ViewPagerAdapter mViewPagerAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    TextView mHowToUse;
    TextView mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        showHowToUse();
        setContentView(R.layout.activity_main_menu);
        setUpView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHowToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, HowToUseActivity.class);
                startActivity(intent);
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showHowToUse(){
        Intent intent = new Intent(getApplicationContext(), HowToUseActivity.class);
        startActivity(intent);
    }

    private void setUpView(){
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mHowToUse = findViewById(R.id.how_to_use);
        mSettings = findViewById(R.id.setting);
    }

    public static MainMenuActivity getMainManuActivity(Context context){
        if(context instanceof MainMenuActivity) {
            return (MainMenuActivity) context;
        }else{
            return null;
        }
    }


    public boolean checkPermission(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }else{
            return true;
        }
        return false;
    }
}
