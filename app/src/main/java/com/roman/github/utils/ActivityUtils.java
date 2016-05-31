package com.roman.github.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Anna on 27.05.2016.
 */
public class ActivityUtils {

    public static void addFragment(FragmentManager fm, Fragment fragment, int container, String tag) {
        if(tag == null) {
            fm.beginTransaction().add(container, fragment, tag).commit();
        } else {
            fm.beginTransaction().add(container, fragment, tag).addToBackStack(tag).commit();
        }
    }

    public static void replaceFragment(FragmentManager fm, Fragment fragment, int container, String tag) {
        if(tag == null) {
            fm.beginTransaction().replace(container, fragment, tag).commit();
        } else {
            fm.beginTransaction().replace(container, fragment, tag).addToBackStack(tag).commit();
        }
    }
}
