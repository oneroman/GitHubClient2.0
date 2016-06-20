package com.roman.github.leakcanary;

import android.util.Log;

import com.squareup.leakcanary.CanaryLog;

/**
 * Created by Anna on 20.06.2016.
 */
public class EnableLogger implements CanaryLog.Logger {

    @Override
    public void d(String message, Object... args) {
        String formatted = String.format(message, args);
        if(formatted.length() < 4000) {
            Log.d("LeakCanary", formatted);
        } else {
            String[] lines = formatted.split("\n");
            String[] arr$ = lines;
            int len$ = lines.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String line = arr$[i$];
                Log.d("LeakCanary", line);
            }
        }

    }

    @Override
    public void d(Throwable throwable, String message, Object... args) {
        this.d(String.format(message, args) + '\n' + Log.getStackTraceString(throwable), new Object[0]);
    }
}
