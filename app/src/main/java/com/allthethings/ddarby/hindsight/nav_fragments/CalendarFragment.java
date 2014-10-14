package com.allthethings.ddarby.hindsight.nav_fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.listeners.CalendarOnSelectListener;

/**
 * Created by ddarby on 10/12/14.
 */
public class CalendarFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        setHasOptionsMenu(true);
        CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarOnSelectListener(getActivity()));
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calender, menu);
    }
}