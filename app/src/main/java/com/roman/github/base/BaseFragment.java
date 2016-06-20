package com.roman.github.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roman.github.AppComponent;
import com.roman.github.MyApplication;
import com.roman.github.utils.Logger;
import com.roman.github.views.BackKeyListener;

/**
 * Created by Anna on 27.05.2016.
 */
abstract public class BaseFragment extends Fragment{

    private static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setupDI();
    }

    protected abstract void setupDI();

    protected AppComponent getAppComponent() {
        return MyApplication.getAppComponent(getContext());
    }

    protected void setToolBar(Toolbar toolbar) {
        if(toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
    }

    protected void showToolBarBackButton(Toolbar toolbar) {
        if(toolbar != null) {
            setToolBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d(TAG, "Toolbar :: onClick back [" + v + "]");
                    handleBackKey();
                }
            });
        }
    }

    private boolean handleBackKey() {
        boolean res = false;
        if(this instanceof BackKeyListener) {
            getActivity().onBackPressed();
            res = true;
        }
        return res;
    }
}
