package com.allthethings.ddarby.hindsight.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allthethings.ddarby.hindsight.R;

/**
 * Created by ddarby on 10/12/14.
 */
public class AlarmFragment extends BaseFragment {

    public static final String TAG = "alarm";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        return rootView;
    }

}