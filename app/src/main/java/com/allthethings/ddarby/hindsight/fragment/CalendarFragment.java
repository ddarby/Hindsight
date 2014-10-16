package com.allthethings.ddarby.hindsight.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.allthethings.ddarby.hindsight.R;

import java.lang.reflect.Field;

/**
 * Created by ddarby on 10/12/14.
 */
public class CalendarFragment extends BaseFragment implements CalendarView.OnDateChangeListener {

    public static final String TAG = "calendar";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        try {
            Class<?> cvClass = calendarView.getClass();
            Field field = cvClass.getDeclaredField("mMonthName");
            field.setAccessible(true);
            try {
                TextView tv = (TextView) field.get(calendarView);
                tv.setTextColor(getResources().getColor(R.color.theme_white));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calender, menu);
    }

    /**
     * CalendarView.OnDateChangeListener Callbacks
     */
    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        Bundle args = new Bundle();
        args.putLong("date", view.getDate());
        EventDialogFragment eventDialog = new EventDialogFragment();
        eventDialog.setArguments(args);
        eventDialog.show(getChildFragmentManager(), EventDialogFragment.TAG);
    }
}