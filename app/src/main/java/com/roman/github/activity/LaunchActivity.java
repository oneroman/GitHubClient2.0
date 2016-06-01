package com.roman.github.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.roman.github.R;
import com.roman.github.base.BaseActivity;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.roman.github.views.BackKeyListener;
import com.roman.github.views.controllers.SplashFragment;

public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate, savedInstanceState [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            Fragment fragment = new SplashFragment();
            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, getFragmentId(), null );
        }
    }

    private int getFragmentId() {
        return R.id.fragment_container;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentId());
    }

    @Override
    public void onBackPressed() {
        Logger.d(TAG, "onBackPressed");
        Fragment fragment = getCurrentFragment();
        if (fragment != null && fragment instanceof BackKeyListener) {
            ((BackKeyListener) fragment).onBackKey();
        }
        super.onBackPressed();
    }

}
