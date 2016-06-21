package com.roman.github.utils;

import android.util.Log;

import com.roman.github.BuildConfig;
import com.roman.github.settings.DevelopersSettings;

/**
 * Created by Anna on 28.05.2016.
 */
final public class Logger {

    public static void d(String TAG, String msg) {
        if(BuildConfig.DEBUG /*&& DevelopersSettings.APP_LOG.getState()*/) {
            Log.d(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if(BuildConfig.DEBUG /*&& DevelopersSettings.APP_LOG.getState()*/) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if(BuildConfig.DEBUG /*&& DevelopersSettings.APP_LOG.getState()*/) {
            Log.e(TAG, msg);
        }
    }
}
