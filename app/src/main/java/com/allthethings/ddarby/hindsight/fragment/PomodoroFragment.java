package com.allthethings.ddarby.hindsight.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.adapter.PomodoroAdapter;
import com.allthethings.ddarby.hindsight.model.data.PomodoroManager;
import com.allthethings.ddarby.hindsight.widget.stickyheader.stickylistview.StickyListHeadersListView;

/**
 * Created by ddarby on 10/12/14.
 */
public class PomodoroFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "pomodoro";

    private StickyListHeadersListView listView;
    private PomodoroAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pomodoro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (StickyListHeadersListView) view.findViewById(R.id.pomodoro_list);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPomodoros();
    }

    @Override
    public void notifyOnPageChange() {
        super.notifyOnPageChange();
        loadPomodoros();
    }

    public void loadPomodoros() {
        if (adapter == null) {
            adapter = new PomodoroAdapter(getActivity(), PomodoroManager.getInstance().getAllPomodoros());
            listView.setAdapter(adapter);
        } else {
            adapter.setPomodoroList(PomodoroManager.getInstance().getAllPomodoros());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // onItemClick
    }
}