package com.roman.github.views.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.roman.github.R;
import com.roman.github.base.BaseFragment;
import com.roman.github.modules.SplashModule;
import com.roman.github.presenters.Splash;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.roman.github.views.UserLoginFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class SplashFragment extends BaseFragment implements Splash.View {

    private static final String TAG = SplashFragment.class.getSimpleName();

    @Inject
    Splash.Presenter mPresenter;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupDI() {
        getObservable(this).subscribe(new Action1<Object>() {
            @Override
            public void call(Object obj) {
                initialized();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initialized() {
        Logger.d(TAG, "initialized");
        ActivityUtils.removeFragment(getActivity().getSupportFragmentManager(), this);
        ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), new UserLoginFragment(), R.id.fragment_container, UserLoginFragment.class.getName());
    }

    private Observable<Object> getObservable(final SplashFragment fragment) {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {

                Logger.d(TAG, "starts first injection");

//                Debug.startMethodTracing("SplashFragment");
                getAppComponent().plus(new SplashModule(fragment)).inject(fragment);
//                Debug.stopMethodTracing();

                return Observable.just(new Object());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
