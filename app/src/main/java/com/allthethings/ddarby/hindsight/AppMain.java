package com.allthethings.ddarby.hindsight;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.allthethings.ddarby.hindsight.adapters.NavigationPagerAdapter;
import com.allthethings.ddarby.hindsight.listeners.NavigationTabListener;
import com.allthethings.ddarby.hindsight.listeners.ViewPagerOnPageListener;
import com.allthethings.ddarby.hindsight.nav_fragments.AlarmFragment;
import com.allthethings.ddarby.hindsight.nav_fragments.CalendarFragment;
import com.allthethings.ddarby.hindsight.nav_fragments.HomeFragment;
import com.allthethings.ddarby.hindsight.nav_fragments.PomodoroFragment;
import com.allthethings.ddarby.hindsight.nav_fragments.SettingsFragment;
import java.util.LinkedHashMap;

public class AppMain extends FragmentActivity {

    private ViewPager viewPager;
    private LinkedHashMap<ActionBar.Tab,Fragment> fragmentMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //created for direct access to Fragment via mapped Tab without iteration and switches
        fragmentMapper = new LinkedHashMap<ActionBar.Tab, Fragment>();
        setupNavigableMapper();
        viewPager.setOnPageChangeListener(new ViewPagerOnPageListener(getActionBar()));
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), fragmentMapper, getActionBar()));
    }

    private void setupNavigableMapper(){
        ActionBar actionBar = getActionBar();
        fragmentMapper.put(actionBar.newTab().setText("Home"), new HomeFragment());
        fragmentMapper.put(actionBar.newTab().setText("Alarm"), new AlarmFragment());
        fragmentMapper.put(actionBar.newTab().setText("Pomodoro"), new PomodoroFragment());
        fragmentMapper.put(actionBar.newTab().setText("Calendar"), new CalendarFragment());
        fragmentMapper.put(actionBar.newTab().setText("Settings"), new SettingsFragment());
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //Add each tab to actionbar and set listener foreach tab in Fragment Mapper
        for(ActionBar.Tab tab : fragmentMapper.keySet()){
            actionBar.addTab(tab.setTabListener(new NavigationTabListener(viewPager)));
        }
    }
}
