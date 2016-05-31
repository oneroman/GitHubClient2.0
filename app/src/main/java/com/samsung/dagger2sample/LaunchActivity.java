package com.samsung.dagger2sample;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.samsung.dagger2sample.base.BaseActivity;
import com.samsung.dagger2sample.utils.ActivityUtils;
import com.samsung.dagger2sample.utils.Logger;
import com.samsung.dagger2sample.views.UserLoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate, savedInstanceState [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            UserLoginFragment fragment = new UserLoginFragment();
            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.fragment_container, null );
        }
    }

}
