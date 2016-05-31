package com.samsung.dagger2sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.samsung.dagger2sample.base.BaseActivity;
import com.samsung.dagger2sample.utils.ActivityUtils;
import com.samsung.dagger2sample.utils.Logger;
import com.samsung.dagger2sample.views.BackKeyListener;
import com.samsung.dagger2sample.views.UserLoginFragment;

public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate, savedInstanceState [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            Fragment fragment = new UserLoginFragment();
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
