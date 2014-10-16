package com.allthethings.ddarby.hindsight.fragment;

import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

/**
 * Created by Garrett on 10/16/14.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyOnPageChange() {
    }
}
