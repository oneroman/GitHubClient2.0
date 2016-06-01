package com.roman.github.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.jakewharton.rxbinding.support.design.widget.RxTextInputLayout;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.roman.github.AppComponent;
import com.roman.github.BuildConfig;
import com.roman.github.R;
import com.roman.github.components.DaggerUserLoginComponent;
import com.roman.github.components.UserLoginComponent;
import com.roman.github.data.UserInfoData;
import com.roman.github.modules.UserLoginModule;
import com.roman.github.base.BaseFragment;
import com.roman.github.presenters.UserLogin;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.roman.github.utils.TextUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginFragment extends BaseFragment implements UserLogin.View, BackKeyListener {

    private static final String TAG = UserLoginFragment.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.userName)
    TextInputEditText userName;
    @BindView(R.id.userName_layout)
    TextInputLayout username_layout;
    @BindView(R.id.button_start)
    Button button_start;

    private ProgressDialog mProgressDialog;
    private boolean animate = true;

    private UserLoginComponent mUserLoginComponent;
    @Inject
    UserLogin.Presenter mPresenter;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupDI() {
        AppComponent appComponent = getAppComponent();
        mUserLoginComponent = DaggerUserLoginComponent.builder().appComponent(appComponent)
                .userLoginModule(new UserLoginModule(this, appComponent.gitHubAPI()))
                .build();
        mUserLoginComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if(savedInstanceState == null && animate == true) {
            animate = false;
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    startInitAnimation();
                    return true;
                }
            });
        }

        if(BuildConfig.DEBUG && savedInstanceState == null) {
            String tmpName = getResources().getString(R.string.default_debug_username);
            RxTextView.text(userName).call(tmpName);
            userName.setSelection(tmpName.length());
        }

        subscriptions.add(RxTextView.textChangeEvents(userName).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUsernameObserver()));

        return view;
    }

    private void startInitAnimation() {
        username_layout.setScaleY(0.0f);
        button_start.setScaleY(0.0f);

        //move toolbar out of screen
        toolbar.setTranslationY(-toolbar.getHeight());

        //animate it
        toolbar.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setStartDelay(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateContent();
                    }
                })
                .start();
    }

    private void animateContent() {
        username_layout.animate()
                .scaleY(1.0f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        button_start.animate()
                .scaleY(1.0f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void exitAnimation() {
        toolbar.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .start();

        username_layout.animate()
                .scaleY(0.0f)
                .setDuration(300)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        button_start.animate()
                .scaleY(0.0f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private Action1<TextViewTextChangeEvent> getUsernameObserver() {
        return new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                String user = textViewTextChangeEvent.text().toString();
                Logger.d(TAG, "updated username [" + user + "]");

                mPresenter.setUsername(user);
                final boolean failed = TextUtils.isEmpty(user);
                RxTextInputLayout.error(username_layout).call(failed ? getResources().getString(R.string.username_empty_error) : null);
            }
        };
    }

    @OnClick(R.id.button_start)
    public void onStartClick() {
        Logger.d(TAG, "onStartClick");

        mPresenter.validateUserinfo();
    }

    @Override
    public void wrongUsername() {
        username_layout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
    }

    @Override
    public void onDestroyView() {
        Logger.d(TAG, "onDestroyView");
        super.onDestroyView();
        subscriptions.clear();
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void requestingUserinfo() {
        mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_title_request_userifo),
                getResources().getString(R.string.please_wait), true);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showUserinfo(UserInfoData userinfo) {
        Logger.d(TAG, "showUserinfo [" + userinfo + "]");
        hideProgress();

        if(userinfo == null) {
            showWrongUserInfo();
        } else {
            ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), RepositoriesListFragment.newInstance(userinfo), R.id.fragment_container, RepositoriesListFragment.class.getName());
        }
    }

    private void showWrongUserInfo() {
        CoordinatorLayout coordinatorLayout = ButterKnife.findById(getActivity(), R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.error_user_does_not_exist, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onBackKey() {
        exitAnimation();
    }
}
