package com.allthethings.ddarby.hindsight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ddarby on 10/12/14.
 */
public class AlarmFragment extends Fragment implements FragmentHandler {

    private String tag;
    private String homeTag;
    private int containerId;
    private static FragmentHandler homeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alarm_fragment, container, false);
        return rootView;
    }
}