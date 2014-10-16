package com.allthethings.ddarby.hindsight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.allthethings.ddarby.hindsight.fragment.AlarmFragment;
import com.allthethings.ddarby.hindsight.fragment.CalendarFragment;
import com.allthethings.ddarby.hindsight.fragment.HomeFragment;
import com.allthethings.ddarby.hindsight.fragment.PomodoroFragment;
import com.allthethings.ddarby.hindsight.fragment.SettingsFragment;

/**
 * Created by ddarby on 10/14/14.
 */
public class NavigationPagerAdapter extends FragmentPagerAdapter {

    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        // ViewPager handles storing fragments in FragmentManager. We do not need to keep references. We can pull references when needed (see HomeActivity)
        switch (position) {
            case 1:
                return new AlarmFragment();
            case 2:
                return new PomodoroFragment();
            case 3:
                return new CalendarFragment();
            case 4:
                return new SettingsFragment();
            case 0:
            default:
                return new HomeFragment();
        }
    }
}