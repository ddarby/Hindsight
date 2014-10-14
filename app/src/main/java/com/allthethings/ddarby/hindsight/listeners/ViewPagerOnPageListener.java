package com.allthethings.ddarby.hindsight.listeners;

import android.app.ActionBar;
import android.support.v4.view.ViewPager;

/**
 * Created by ddarby on 10/14/14.
 */
public class ViewPagerOnPageListener implements ViewPager.OnPageChangeListener{
    private ActionBar actionBar;

    public ViewPagerOnPageListener(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
