package com.allthethings.ddarby.hindsight.listeners;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * Created by ddarby on 10/14/14.
 */
public class NavigationTabListener implements ActionBar.TabListener {
    private ViewPager viewPager;

    public NavigationTabListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
}