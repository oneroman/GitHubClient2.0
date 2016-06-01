package com.roman.github.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.roman.github.AppComponent;
import com.roman.github.MyApplication;

/**
 * Created by Anna on 27.05.2016.
 */
abstract public class BaseFragment extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDI();
    }

    protected abstract void setupDI();

    protected AppComponent getAppComponent() {
        return MyApplication.getAppComponent(getContext());
    }
}
