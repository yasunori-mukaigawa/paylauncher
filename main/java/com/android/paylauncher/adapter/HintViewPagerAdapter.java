package com.android.paylauncher.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.paylauncher.fragment.hint.HintPageFragment;

public class HintViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int MAX_PAGE_NUM = 3;

    public HintViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new HintPageFragment(position);
    }

    @Override
    public int getCount() {
        return MAX_PAGE_NUM;
    }
}
