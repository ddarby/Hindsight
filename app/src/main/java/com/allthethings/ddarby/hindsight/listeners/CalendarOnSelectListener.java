package com.allthethings.ddarby.hindsight.listeners;

import android.content.Context;
import android.widget.CalendarView;

import com.allthethings.ddarby.hindsight.EventDialog;

/**
 * Created by ddarby on 10/14/14.
 */
public class CalendarOnSelectListener implements CalendarView.OnDateChangeListener{
    private Context context;
    public CalendarOnSelectListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        new EventDialog(context, view.getDate()).create().show();

    }
}
