package com.allthethings.ddarby.hindsight.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.model.Pomodoro;
import com.allthethings.ddarby.hindsight.widget.stickyheader.stickylistview.StickyListHeadersAdapter;

import java.util.List;

/**
 * Created by Garrett on 10/16/14.
 */
public class PomodoroAdapter extends ArrayAdapter<Pomodoro> implements StickyListHeadersAdapter {

    private List<Pomodoro> pomodoroList;

    public PomodoroAdapter(Context context, List<Pomodoro>pomodoroList) {
        super(context, android.R.layout.simple_list_item_1);
        this.pomodoroList = pomodoroList;
    }

    public void setPomodoroList(List<Pomodoro> pomodoroList) {
        this.pomodoroList = pomodoroList;
        notifyDataSetChanged();
    }

    @Override
    public Pomodoro getItem(int position) {
        return pomodoroList.get(position);
    }

    @Override
    public int getCount() {
        return pomodoroList.size();
    }

    @Override
    public long getHeaderId(int position) {
        return position/25;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        int count = 25;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_pomodoro_list_item_header_title, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.layout_pomodoro_list_item_header_title);
        int toValue = ((position/count)*count+count);
        if (toValue > pomodoroList.size()) {
            toValue = pomodoroList.size();
        }

        tv.setText(((position / count) * count + 1) + " to " + toValue);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_pomodoro_item, null);
        }

        Pomodoro pomodoro = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.pomodoro_title);
        title.setText(pomodoro.getTitle());

        TextView date = (TextView) convertView.findViewById(R.id.pomodoro_date);
        try {
            DateFormat df = new DateFormat();
            date.setText("Date: " + df.format("yyyy-MM-dd", pomodoro.getTimestamp()).toString());
        } catch (Throwable t) {
            date.setText(date.getText() + pomodoro.getTimestamp().toString());
        }

        return convertView;
    }
}
