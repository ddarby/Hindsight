package com.allthethings.ddarby.hindsight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class AppMain extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ActionBar.Tab homeTab;
    private ActionBar.Tab alarmTab;
    private ActionBar.Tab pomodoroTab;
    private ActionBar.Tab calenderTab;
    private ActionBar.Tab settingsTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager()));
        setupNavigation();
    }

    private void setupNavigation() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        homeTab = actionBar.newTab().setText("Home");
        alarmTab = actionBar.newTab().setText("Alarm");
        pomodoroTab = actionBar.newTab().setText("Pomodoro");
        calenderTab = actionBar.newTab().setText("Calendar");
        settingsTab = actionBar.newTab().setText("Settings");

        homeTab.setTabListener(new NavigationTabListener());
        alarmTab.setTabListener(new NavigationTabListener());
        pomodoroTab.setTabListener(new NavigationTabListener());
        calenderTab.setTabListener(new NavigationTabListener());
        settingsTab.setTabListener(new NavigationTabListener());

        actionBar.addTab(homeTab);
        actionBar.addTab(alarmTab);
        actionBar.addTab(pomodoroTab);
        actionBar.addTab(calenderTab);
        actionBar.addTab(settingsTab);
    }

    @Override
    public void onPageSelected(int position) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {}

    @Override
    public void onPageScrollStateChanged(int i) {}

    private class NavigationTabListener implements ActionBar.TabListener {

        public NavigationTabListener() {}

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
    }

    private class NavigationPagerAdapter extends FragmentPagerAdapter {

        public NavigationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new AlarmFragment();
                case 2:
                    return new PomodoroFragment();
                case 3:
                    return new CalendarFragment();
                case 4:
                    return new SettingsFragment();
                default:
                    return new HomeFragment();
            }
        }
    }
}
