package com.android.paylauncher.util;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class PageSwipeTransformer implements ViewPager.PageTransformer {
    private float minAlphaOut = 0.0F;
    private float minAlphaIn = 0.0F;
    private float minScaleOut = 0.8F;
    private float minScaleIn = 0.8F;
    private float positionXOffsetIn = 0.8F;
    private float positionXOffsetOut = -0.8F;
    private float positionYOffsetIn = 0.0F;
    private float positionYOffsetOut = 0.0F;

    @Override
    public void transformPage(@NonNull View page, float position) {
        final float width = (float) page.getWidth();
        final float height = (float) page.getHeight();

        if (-1.0F < position && position <= 0) {
            page.setVisibility(View.VISIBLE);
            page.setAlpha(Math.max(minAlphaOut, 1 + position));
            page.setScaleX(Math.max(minScaleOut, 1 + position));
            page.setScaleY(Math.max(minScaleOut, 1 + position));
            page.setTranslationX(width * -position + width * -position * positionXOffsetOut);
            page.setTranslationY(height * -position * positionYOffsetOut);
        }

        if (0 < position && position < 1.0F) {
            page.setVisibility(View.VISIBLE);
            page.setAlpha(Math.max(minAlphaIn, 1 - position));
            page.setScaleX(Math.max(minScaleIn, 1 - position));
            page.setScaleY(Math.max(minScaleIn, 1 - position));
            page.setTranslationX(-width * position + width * position * positionXOffsetIn);
            page.setTranslationY(height * position * positionYOffsetIn);
        }

        if (position <= -1.0F || 1.0F <= position) {
            page.setVisibility(View.GONE);
        }
    }
}