package com.android.paylauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.paylauncher.adapter.ViewPagerAdapter;
import com.android.paylauncher.util.PageSwipeTransformer;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private TextView mHowToUse;
    private TextView mSettings;
    private final String PREF_HOW_TO_USE = "pref_how_to_use";
    private final String PREF_KEY = "pref_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        SharedPreferences pref = getSharedPreferences(PREF_HOW_TO_USE,Context.MODE_PRIVATE);
        //初回起動時のみ、ヒントページを表示する
        if(!pref.getBoolean(PREF_KEY,false)) {
            showHowToUse();
            pref.edit().putBoolean(PREF_KEY, true).apply();
        }
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new PageSwipeTransformer());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        int tabCount = tabLayout.getTabCount();
        ArrayList<TabLayout.Tab> tabList = new ArrayList<>();
        for (int i = 0 ; i < tabCount; i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tabList.add(i, tab);
        }
        mHowToUse = findViewById(R.id.how_to_use);
        mSettings = findViewById(R.id.setting);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHowToUse = null;
        mSettings = null;
    }

    public static MainMenuActivity getMainManuActivity(Context context){
        if(context instanceof MainMenuActivity) {
            return (MainMenuActivity) context;
        }else{
            return null;
        }
    }


    private boolean checkPermission(){
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
