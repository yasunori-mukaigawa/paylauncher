package com.android.paylauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.paylauncher.adapter.HintViewPagerAdapter;
import com.android.paylauncher.util.PageSwipeTransformer;

public class HowToUseActivity extends AppCompatActivity {
    public ViewPager mViewPager;
    public Button mSkipButton;
    public Button mBackButton;
    public Button mNextButton;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use_activity);
        setUpView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewPagerChangeListener();
        buttonClickListener();
    }


    private void setUpView(){
        HintViewPagerAdapter viewPagerAdapter = new HintViewPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.hint_pager);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setPageTransformer(true, new PageSwipeTransformer());
        mTitle = findViewById(R.id.base_hint_title);
        mSkipButton = findViewById(R.id.skip);
        mBackButton = findViewById(R.id.back);
        mBackButton.setVisibility(View.GONE);
        mNextButton = findViewById(R.id.next);
    }

    private void viewPagerChangeListener(){
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setButtonAppearance(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void buttonClickListener(){
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = mViewPager.getCurrentItem();
                if(page != 0) {
                    mViewPager.setCurrentItem(page -1, true);
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = mViewPager.getCurrentItem();
                if(page == 2){
                    finish();
                } else {
                    mViewPager.setCurrentItem(page + 1, true);
                }
            }
        });
    }

    public void setButtonAppearance(int position){
        switch (position){
            case 0:
                mBackButton.setVisibility(View.GONE);
                break;
            case 1:
                mBackButton.setVisibility(View.VISIBLE);
                mSkipButton.setVisibility(View.VISIBLE);
                mNextButton.setText(R.string.hint_page_next);
                break;
            case 2:
                mNextButton.setText(R.string.hint_page_exit);
                mSkipButton.setVisibility(View.GONE);
        }
    }
}
