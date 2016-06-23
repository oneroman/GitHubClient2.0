package com.roman.github.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.roman.github.R;
import com.roman.github.base.BaseFragment;
import com.roman.github.modules.SplashModule;
import com.roman.github.presenters.Splash;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.roman.github.views.splash.DotsView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * That Fragment is necessary to perform injection of heavy objects while application start up time and shows animation to user
 * That shall happen only while application first launch time. as soon as app process exists then injection is not applicable and Fragment will be skipped.
 */
public class SplashFragment extends BaseFragment implements Splash.View {

    private static final String TAG = SplashFragment.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.vDotsView)
    DotsView dotsView;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerContainer;

    private ObjectAnimator dotsAnimator;
    private final static int MAX_INJECTION_DURATION = 1000;

    @Inject
    Splash.Presenter mPresenter;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupDI() {
        getObservable(this).mergeWith(getTimerObservable()).skip(1).subscribe(new Action1<Object>() {
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
        unbinder = ButterKnife.bind(this, view);
        animate();

        return view;
    }

    private void initialized() {
        Logger.d(TAG, "initialized");
        ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), new UserLoginFragment(), R.id.fragment_container, null);
    }

    private Observable<Object> getObservable(final SplashFragment fragment) {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {

                Logger.d(TAG, "starts first injection");

//                Debug.startMethodTracing("SplashFragment");
                long currentTime = System.currentTimeMillis();
                getAppComponent().plus(new SplashModule(fragment)).inject(fragment);
                Logger.d(TAG, "injection is done for [" + (System.currentTimeMillis() - currentTime) + "] ms");
//                Debug.stopMethodTracing();

                return Observable.just(new Object());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Object> getTimerObservable() {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                try {
                    Thread.sleep(MAX_INJECTION_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just(new Object());
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dotsAnimator.cancel();
        dotsAnimator = null;
        unbinder.unbind();unbinder = null;
    }

    private void animate() {
        if(dotsAnimator == null) {
            dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(MAX_INJECTION_DURATION);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        dotsAnimator.cancel();
        dotsAnimator.start();
    }

    @Override
    public void onResume() {
        Logger.d(TAG, "onResume");
        super.onResume();
        shimmerContainer.setRepeatMode(ValueAnimator.REVERSE);
        shimmerContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        Logger.d(TAG, "onPause");
        super.onPause();
        shimmerContainer.stopShimmerAnimation();
    }
}
