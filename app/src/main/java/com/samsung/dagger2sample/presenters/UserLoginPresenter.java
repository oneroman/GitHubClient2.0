package com.samsung.dagger2sample.presenters;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginPresenter implements UserLogin.Presenter {

    private static final String TAG = UserLoginPresenter.class.getSimpleName();

    private UserLogin.View mView;

    private String mUsername;

    @Override
    public void setView(UserLogin.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

    @Override
    public String getUsername() {
        return mUsername;
    }

    @Override
    public void setUsername(String value) {
        mUsername = value;
    }
}
