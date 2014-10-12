package com.allthethings.ddarby.hindsight;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;


public class AppMain extends Activity implements View.OnClickListener{
    private Map<Integer, FragmentHandler> fragMap;
    private static HomeFragment home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        fragMap = new HashMap<Integer, FragmentHandler>();

        Button homeButton = (Button) findViewById(R.id.homeButton);
        Button alarmButton = (Button) findViewById(R.id.alarmButton);
        Button pomodoroButton = (Button) findViewById(R.id.pomodoroButton);
        Button calendarButton = (Button) findViewById(R.id.calendarButton);
        Button settingButton = (Button) findViewById(R.id.settingsButton);

        homeButton.setOnClickListener(this);
        alarmButton.setOnClickListener(this);
        pomodoroButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);

        home =  new HomeFragment();
        AlarmFragment alarmFrag = new AlarmFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        SettingsFragment settingsFrag = new SettingsFragment();
        PomodoroFragment pomodoroFragment = new PomodoroFragment();

        addToFragMap(homeButton.getId(), home, null);
        addToFragMap(settingButton.getId(), settingsFrag, home);
        addToFragMap(alarmButton.getId(), alarmFrag, home);
        addToFragMap(calendarButton.getId(), calendarFragment, home);
        addToFragMap(pomodoroButton.getId(), pomodoroFragment, home);

        setUpFragBundle(homeButton.getTag().toString(), "homeButton", R.id.fragment_placeholder, home);
        setUpFragBundle(alarmButton.getTag().toString(), "homeButton", R.id.fragment_placeholder, alarmFrag);
        setUpFragBundle(pomodoroButton.getTag().toString(), "homeButton", R.id.fragment_placeholder, pomodoroFragment);
        setUpFragBundle(settingButton.getTag().toString(), "homeButton", R.id.fragment_placeholder, settingsFrag);
        setUpFragBundle(calendarButton.getTag().toString(), "homeButton", R.id.fragment_placeholder, calendarFragment);
        home.handleTransition(getFragmentManager());
    }

    @Override
    public void onClick(View view) {
        fragMap.get(view.getId()).handleTransition(getFragmentManager());
    }
    public void addToFragMap(int fragLayoutId, FragmentHandler frag, FragmentHandler homeFrag){
        frag.setHomeFragment(homeFrag);
        fragMap.put(fragLayoutId, frag);
    }

    public void setUpFragBundle(String fragTag, String homeTag, int container, Fragment argsFor){
        Bundle bundle = new Bundle();
        bundle.putString("HOME", homeTag);
        bundle.putString("TAG", fragTag);
        bundle.putInt("CONTAINER", container);
        argsFor.setArguments(bundle);
    }
}
