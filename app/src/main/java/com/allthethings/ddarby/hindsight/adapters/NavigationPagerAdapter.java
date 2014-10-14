package com.allthethings.ddarby.hindsight.adapters;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ddarby on 10/14/14.
 */
public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private LinkedHashMap<ActionBar.Tab, Fragment> fragmentMapper;
    private ActionBar actionBar;

    public NavigationPagerAdapter(FragmentManager fm, LinkedHashMap<ActionBar.Tab, Fragment> fragmentMapper, ActionBar actionBar) {
        super(fm);
        this.fragmentMapper = fragmentMapper;
        this.actionBar = actionBar;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentMapper.get(actionBar.getTabAt(position));
    }
}