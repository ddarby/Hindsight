package com.allthethings.ddarby.hindsight;

import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by ddarby on 10/12/14.
 */
public class CalendarFragment extends Fragment implements FragmentHandler {

    private String tag;
    private String homeTag;
    private int containerId;
    private static FragmentHandler homeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        setHasOptionsMenu(true);
        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                new EventDialog(getActivity(), view.getDate()).create().show();
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calender, menu);
    }

    public void setHomeFragment(FragmentHandler fragment) {
        homeFragment = fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.homeTag = getArguments().getString("HOME");
        this.tag = getArguments().getString("TAG");
        this.containerId = getArguments().getInt("CONTAINER");
    }

    @Override
    public void handleTransition(FragmentManager fragMan){
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter_left, R.anim.exit_left);
        Log.d("IS IT VISIBLE ALREADY? ",""+this.isVisible());
        if(!this.isVisible()) {
            homeFragment.handleTransition(fragMan);
            fragTrans.replace(containerId, this, tag);
            fragTrans.commit();
            return;
        }
        homeFragment.handleTransition(getFragmentManager());
    }
}