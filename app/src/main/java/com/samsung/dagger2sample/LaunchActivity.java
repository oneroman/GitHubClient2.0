package com.samsung.dagger2sample;

import android.os.Bundle;

import com.samsung.dagger2sample.base.BaseActivity;
import com.samsung.dagger2sample.utils.ActivityUtils;
import com.samsung.dagger2sample.views.UserLoginFragment;

public class LaunchActivity extends BaseActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife.bind(this);

        if(savedInstanceState == null) {
//            UserLoginFragment fragment = new UserLoginFragment();
            BlankFragment fragment = new BlankFragment();
            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.fragment_container, null );
        }
    }

}
