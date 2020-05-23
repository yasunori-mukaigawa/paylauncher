package com.android.paylauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.android.paylauncher.adapter.HintViewPagerAdapter;

public class HowToUseActivity extends AppCompatActivity {
    HintViewPagerAdapter mViewPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use_activity);

        mViewPagerAdapter = new HintViewPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.hint_pager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }
}
