package com.roman.github.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Anna on 27.05.2016.
 */
public class ActivityUtils {

    private static final String TAG = ActivityUtils.class.getSimpleName();

    public static void addFragment(FragmentManager fm, Fragment fragment, int container, String tag) {
        Logger.d(TAG, "addFragment [" + fragment + "]");
        if(tag == null) {
            fm.beginTransaction().add(container, fragment, tag).commit();
        } else {
            fm.beginTransaction().add(container, fragment, tag).addToBackStack(tag).commit();
        }
    }

    public static void replaceFragment(FragmentManager fm, Fragment fragment, int container, String tag) {
        Logger.d(TAG, "replaceFragment [" + fragment + "]");
        if(tag == null) {
            fm.beginTransaction().replace(container, fragment, tag).commit();
        } else {
            fm.beginTransaction().replace(container, fragment, tag).addToBackStack(tag).commit();
        }
    }

    public static void removeFragment(FragmentManager fm, Fragment fragment) {
        Logger.d(TAG, "removeFragment [" + fragment + "]");
        fm.beginTransaction().remove(fragment).commit();
    }
}
