package com.samsung.dagger2sample.utils;

import android.util.Log;

import com.samsung.dagger2sample.BuildConfig;

/**
 * Created by Anna on 28.05.2016.
 */
final public class Logger {

    public static void d(String TAG, String msg) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if(BuildConfig.DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if(BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
