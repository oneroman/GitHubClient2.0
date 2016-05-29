package com.samsung.dagger2sample.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding.support.design.widget.RxTextInputLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.samsung.dagger2sample.BuildConfig;
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
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginFragment extends BaseFragment implements UserLogin.View {

    private static final String TAG = UserLoginFragment.class.getSimpleName();

    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userName_layout)
    TextInputLayout username_layout;
    @BindView(R.id.button_start)
    View mButtonStart;


    @Inject
    UserLogin.Presenter mPresenter;

    private Subscription usernameChangeSubscription;

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setup() {
        ((MyApplication) getActivity().getApplication()).getRepositoriesListComponent().inject(this);
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

        usernameChangeSubscription = RxTextView.textChangeEvents(userName).subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                String user = textViewTextChangeEvent.text().toString();
                Logger.d(TAG, "updated username [" + user + "]");

                mPresenter.setUsername(user);
                final boolean failed = TextUtils.isEmpty(user);
                RxTextInputLayout.error(username_layout).call(failed ? getResources().getString(R.string.username_empty_error) : null);
//                RxView.enabled(mButtonStart).call(!failed);
            }
        });

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
            ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), RepositoriesListFragment.newInstance(mPresenter.getUsername()), R.id.fragment_container, "RepositoriesListFragment");
        }
    }

    @Override
    public void onDestroyView() {
        Logger.d(TAG, "onDestroyView");
        super.onDestroyView();
        usernameChangeSubscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
