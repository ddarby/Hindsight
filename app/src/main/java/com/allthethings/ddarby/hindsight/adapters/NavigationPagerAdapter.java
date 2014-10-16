package com.allthethings.ddarby.hindsight.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ddarby on 10/14/14.
 */
public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private Controller controller;

    public NavigationPagerAdapter(FragmentManager fm, Controller controller) {
        super(fm);
        this.controller = controller;
    }

    @Override
    public int getCount() {
        return controller.getCount();
    }

    @Override
    public Fragment getItem(int position) {
        return controller.getItem(position);
    }

    public interface Controller {
        public int getCount();
        public Fragment getItem(int position);
    }
}