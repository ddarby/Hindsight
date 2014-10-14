package com.allthethings.ddarby.hindsight.nav_fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.allthethings.ddarby.hindsight.Pomodoro;
import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.Task;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ddarby on 10/12/14.
 */
public class PomodoroFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pomodora_fragment, container, false);
//        ListView listView = (ListView) rootView.findViewById(R.id.pomodoroList);
//
//        listView.setAdapter(new ArrayAdapter<>());
        return rootView;
    }
    public List<Pomodoro> loadPomodoros(){
        //Fake Data TODO: Pull from DB
        ArrayList<Pomodoro> pomodoroList = new ArrayList<Pomodoro>(5);
        ArrayList<Task> taskList = new ArrayList<Task>(5);
        for (int i=0; i<pomodoroList.size(); i++) {
            for (int j = 0; j < taskList.size(); j++) {
                taskList.add(new Task("SomeTask-"+i+"-"+j, "Some Things to Do"+i+"-"+j, false));
            }
            pomodoroList.add(new Pomodoro(taskList, new Date(), "Pomodoro Nmber"+i));
        }
        return pomodoroList;
    }
}