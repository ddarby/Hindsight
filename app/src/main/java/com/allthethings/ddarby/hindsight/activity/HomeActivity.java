package com.allthethings.ddarby.hindsight.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.adapter.NavigationPagerAdapter;
import com.allthethings.ddarby.hindsight.fragment.AlarmFragment;
import com.allthethings.ddarby.hindsight.fragment.BaseFragment;
import com.allthethings.ddarby.hindsight.fragment.CalendarFragment;
import com.allthethings.ddarby.hindsight.fragment.HomeFragment;
import com.allthethings.ddarby.hindsight.fragment.PomodoroFragment;
import com.allthethings.ddarby.hindsight.fragment.SettingsFragment;

import java.util.LinkedHashMap;

public class HomeActivity extends ActionBarActivity implements NavigationPagerAdapter.Controller, ViewPager.OnPageChangeListener, ActionBar.TabListener {

    private final String PREFS_FILE = "HindsightPrefs";
    private final String LAST_SELECTED_TAB = "lastSelectedTab";

    private ViewPager viewPager;
    private LinkedHashMap<ActionBar.Tab, Fragment> fragmentMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //created for direct access to Fragment via mapped Tab without iteration and switches
        setupNavigableMapper();

        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        getActionBar().setSelectedNavigationItem(prefs.getInt(LAST_SELECTED_TAB, 0));
    }

    private void setupNavigableMapper() {
        ActionBar actionBar = getActionBar();
        fragmentMapper = new LinkedHashMap<ActionBar.Tab, Fragment>();
        fragmentMapper.put(actionBar.newTab().setText("Home"), new HomeFragment());
        fragmentMapper.put(actionBar.newTab().setText("Alarm"), new AlarmFragment());
        fragmentMapper.put(actionBar.newTab().setText("Pomodoro"), new PomodoroFragment());
        fragmentMapper.put(actionBar.newTab().setText("Calendar"), new CalendarFragment());
        fragmentMapper.put(actionBar.newTab().setText("Settings"), new SettingsFragment());

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //Add each tab to actionbar and set listener foreach tab in Fragment Mapper
        for (ActionBar.Tab tab : fragmentMapper.keySet()) {
            actionBar.addTab(tab.setTabListener(this));
        }
    }

    /**
     * NavigationPagerAdapter.Controller Callbacks
     */
    @Override
    public int getCount() {
        return fragmentMapper.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentMapper.get(getActionBar().getTabAt(position));
    }

    /**
     * ViewPager.OnPageChangeListener Callbacks
     */
    @Override
    public void onPageSelected(int position) {
        getActionBar().setSelectedNavigationItem(position);
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        prefs.edit().putInt(LAST_SELECTED_TAB, getActionBar().getSelectedTab().getPosition()).apply();

        if (viewPager != null && viewPager.getAdapter() != null) {
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                BaseFragment fragment = (BaseFragment) viewPager.getAdapter().instantiateItem(viewPager, i);
                if (fragment != null && fragment.isAdded()) {
                    fragment.notifyOnPageChange();
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {}

    @Override
    public void onPageScrollStateChanged(int i) {}


    /**
     * ActionBar.TabListener Callbacks
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
}
