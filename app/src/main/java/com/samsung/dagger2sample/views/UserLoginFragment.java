package com.samsung.dagger2sample.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding.support.design.widget.RxTextInputLayout;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.samsung.dagger2sample.BuildConfig;
import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;
import com.samsung.dagger2sample.MyApplication;
import com.samsung.dagger2sample.R;
import com.samsung.dagger2sample.base.BaseFragment;
import com.samsung.dagger2sample.presenters.UserLogin;
import com.samsung.dagger2sample.utils.ActivityUtils;
import com.samsung.dagger2sample.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginFragment extends BaseFragment implements UserLogin.View {

    private static final String TAG = UserLoginFragment.class.getSimpleName();

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userName_layout)
    TextInputLayout username_layout;

    private ProgressDialog mProgressDialog;

    @Inject
    UserLogin.Presenter mPresenter;
    @Inject
    GitHubAPI mGitHub;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setup() {
        ((MyApplication) getActivity().getApplication()).getRepositoriesListComponent().inject(this);
        mPresenter.init(mGitHub);
        mPresenter.setView(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, view);

        if(BuildConfig.DEBUG && savedInstanceState == null) {
            String tmpName = getResources().getString(R.string.default_debug_username);
            userName.setText(tmpName);
            userName.setSelection(tmpName.length());
        }

        subscriptions.add(RxTextView.textChangeEvents(userName).subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                String user = textViewTextChangeEvent.text().toString();
                Logger.d(TAG, "updated username [" + user + "]");

                mPresenter.setUsername(user);
                final boolean failed = TextUtils.isEmpty(user);
                RxTextInputLayout.error(username_layout).call(failed ? getResources().getString(R.string.username_empty_error) : null);
            }
        }));

        return view;
    }

    @OnClick(R.id.button_start)
    public void onStartClick() {
        Logger.d(TAG, "onStartClick");
        boolean success = true;
        //need to proceed every input data and animate them if they are wrong one by one
        String name = mPresenter.getUsername();
        if(TextUtils.isEmpty(name)) {
            username_layout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
            success = false;
        }

        //if we validate all data then let's proceed next step
        if (success) {
            mPresenter.getUserinfo();
        }
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

    @Override
    public void showUserinfo(Userinfo userinfo) {
        Logger.d(TAG, "showUserinfo [" + userinfo + "]");
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        if(userinfo == null) {
            showWrongUserInfo();
        } else {
            ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), RepositoriesListFragment.newInstance(userinfo), R.id.fragment_container, "RepositoriesListFragment");
        }
    }

    private void showWrongUserInfo() {
        CoordinatorLayout coordinatorLayout = ButterKnife.findById(getActivity(), R.id.coordinatorLayout);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.error_user_does_not_exist, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
