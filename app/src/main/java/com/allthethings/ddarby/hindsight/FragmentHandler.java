package com.allthethings.ddarby.hindsight;

import android.app.FragmentManager;

/**
 * Created by ddarby on 10/12/14.
 */
public interface FragmentHandler {
    public void setHomeFragment(FragmentHandler fragment);
    public void handleTransition(FragmentManager fragMan);
    public String getTag();
}