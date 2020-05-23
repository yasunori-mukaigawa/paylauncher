package com.android.paylauncher.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.paylauncher.fragment.hint.BaseHintFragment;

public class HintViewPagerAdapter extends FragmentStatePagerAdapter {

    public HintViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new BaseHintFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
