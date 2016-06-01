package com.roman.github.presenters;


/*
* Keep that class for future internal additional initialization while app startup
* */
public class SplashPresenter implements Splash.Presenter {

    private static final String TAG = SplashPresenter.class.getSimpleName();

    private Splash.View mView;

    public SplashPresenter(Splash.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

}
