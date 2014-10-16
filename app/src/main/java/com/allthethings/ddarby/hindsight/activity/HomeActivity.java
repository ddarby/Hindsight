package com.allthethings.ddarby.hindsight.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.adapter.NavigationPagerAdapter;
import com.allthethings.ddarby.hindsight.fragment.BaseFragment;

public class HomeActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener, ActionBar.TabListener {

    private final String PREFS_FILE = "HindsightPrefs";
    private final String LAST_SELECTED_TAB = "lastSelectedTab";

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupNavigation();
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        getActionBar().setSelectedNavigationItem(prefs.getInt(LAST_SELECTED_TAB, 0));
    }

    private void setupNavigation() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Home").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Alarm").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Pomodoro").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Calendar").setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Settings").setTabListener(this));
    }

    /**
     * ViewPager.OnPageChangeListener Callbacks
     */
    @Override
    public void onPageSelected(int position) {
        getActionBar().setSelectedNavigationItem(position);
        SharedPreferences prefs = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        prefs.edit().putInt(LAST_SELECTED_TAB, getActionBar().getSelectedTab().getPosition()).apply();

        // pull Fragment references to call notify since onResume is not called when selecting immediate left/right fragment
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
