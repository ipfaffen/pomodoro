package com.ipfaffen.pomodoro.listener;

import android.support.v4.view.ViewPager;

/**
 * @author Isaias Pfaffenseller
 */
public abstract class OnPageChangeSimpleListener implements ViewPager.OnPageChangeListener {

    @Override
    public abstract void onPageSelected(int position);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}