package com.android.paylauncher.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.paylauncher.R;
import com.android.paylauncher.fragment.LaunchAppsFragment;
import com.android.paylauncher.fragment.NewsListFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_NUM = 2;
    private ArrayList<String> mTitleList = new ArrayList<String>();
    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new LaunchAppsFragment();
                break;
            case 1:
                fragment = new NewsListFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                mTitleList.add(position,mContext.getResources().getString(R.string.app_name));
                break;
            case 1:
                mTitleList.add(position,mContext.getResources().getString(R.string.news));
                break;
        }
        return mTitleList.get(position);
    }
}
