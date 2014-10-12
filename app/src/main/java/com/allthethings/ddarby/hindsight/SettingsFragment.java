package com.allthethings.ddarby.hindsight;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ddarby on 10/12/14.
 */
public class SettingsFragment extends Fragment implements FragmentHandler {

    private String tag;
    private String homeTag;
    private int containerId;
    private static FragmentHandler homeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        return rootView;
    }

    public void setHomeFragment(FragmentHandler fragment) {
        homeFragment = fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.homeTag = getArguments().getString("HOME");
        this.tag = getArguments().getString("TAG");
        this.containerId = getArguments().getInt("CONTAINER");
    }

    @Override
    public void handleTransition(FragmentManager fragMan){
        Fragment fragment = fragMan.findFragmentById(containerId);
        if(fragment!=null){
            Log.d("CURRENT TAG",fragment.getTag());
        }
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter_left, R.anim.exit_left);
        Log.d("IS IT VISIBLE ALREADY? ",""+this.isVisible());
        if(!this.isVisible()) {
            homeFragment.handleTransition(fragMan);
            fragTrans.replace(containerId, this, tag);
            fragTrans.commit();
            return;
        }
        homeFragment.handleTransition(getFragmentManager());
    }
}