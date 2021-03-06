package com.allthethings.ddarby.hindsight.application;

import android.app.Application;

import com.allthethings.ddarby.hindsight.model.data.PomodoroManager;

/**
 * Created by Garrett on 10/15/14.
 */
public class HindsightApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize TaskManager
        PomodoroManager.initializeInstance(this);
    }
}
