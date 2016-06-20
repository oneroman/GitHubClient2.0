package com.roman.github.leakcanary;

import com.squareup.leakcanary.CanaryLog;

/**
 * Created by Anna on 20.06.2016.
 */
public class DisableLogger implements CanaryLog.Logger {

    @Override
    public void d(String s, Object... objects) {

    }

    @Override
    public void d(Throwable throwable, String s, Object... objects) {

    }
}
