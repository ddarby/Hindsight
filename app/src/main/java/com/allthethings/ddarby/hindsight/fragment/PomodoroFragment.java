package com.allthethings.ddarby.hindsight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.model.Pomodoro;
import com.allthethings.ddarby.hindsight.model.data.PomodoroManager;

import java.util.ArrayList;

/**
 * Created by ddarby on 10/12/14.
 */
public class PomodoroFragment extends Fragment {

    private ArrayList<Pomodoro> pomodoroList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pomodoro, container, false);
//        ListView listView = (ListView) rootView.findViewById(R.id.pomodoroList);
//
//        listView.setAdapter(new ArrayAdapter<>());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPomodoros();
    }

    public void loadPomodoros(){
        pomodoroList =  PomodoroManager.getInstance().getAllPomodoros();
    }
}