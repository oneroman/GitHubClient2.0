package com.roman.github.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.jakewharton.rxbinding.support.design.widget.RxTextInputLayout;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.roman.github.BuildConfig;
import com.roman.github.R;
import com.roman.github.data.UserInfoData;
import com.roman.github.modules.UserLoginModule;
import com.roman.github.base.BaseFragment;
import com.roman.github.presenters.DevSettings;
import com.roman.github.presenters.UserLogin;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.roman.github.utils.TextUtils;
import com.roman.github.views.controllers.KeyboardController;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginFragment extends BaseFragment implements UserLogin.View, BackKeyListener, SearchListener {

    private static final String TAG = UserLoginFragment.class.getSimpleName();

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nvView)
    NavigationView navigationView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.userName)
    TextInputEditText userName;
    @BindView(R.id.userName_layout)
    TextInputLayout username_layout;
    @BindView(R.id.button_start)
    Button button_start;

    private ActionBarDrawerToggle mDrawerToggle;

    private Unbinder unbinder;

    private MenuItem mSearchMenuItem;
    private ProgressDialog mProgressDialog;
    private boolean animate = true;

    @Inject
    UserLogin.Presenter mPresenter;
    @Inject
    DevSettings.Presenter mDevSettingsPresenter;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    public UserLoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupDI() {
//        Debug.startMethodTracing("UserLoginFragmentTrace");
        getAppComponent().plus(new UserLoginModule(this)).inject(this);
//        Debug.stopMethodTracing();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        unbinder = ButterKnife.bind(this, view);

        setActionBar();

        mDrawerToggle = setupDrawerToggle();
        setupDrawerContent(navigationView);
        final boolean enableDrawerMenu = BuildConfig.DEBUG;
        mDrawerToggle.setDrawerIndicatorEnabled(enableDrawerMenu);
        drawerLayout.setDrawerLockMode(enableDrawerMenu ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.syncState();

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

        subscriptions.add(RxTextView.textChangeEvents(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUsernameObserver()));

        return view;
    }

    private void setActionBar() {
        setToolBar(toolbar);
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

        KeyboardController.hideKeyboard(getContext(), username_layout);

        mPresenter.validateUserinfo();
    }

    @Override
    public void wrongUsername() {
        username_layout.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDevSettingsPresenter.select(menuItem);
                        return true;
                    }
                });

        mDevSettingsPresenter.init(navigationView.getMenu());

    }

    @Override
    public void noInternetConnection() {
        Logger.d(TAG, "noInternetConnection");
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.error_internet_not_available, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        Logger.d(TAG, "onDestroyView");
        super.onDestroyView();
        //memory leaks
        mSearchMenuItem = null;
        hideProgress();
        unbinder.unbind();unbinder = null;
        subscriptions.clear();
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void requestingUserinfo() {
        mProgressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.dialog_title_request_userifo), getResources().getString(R.string.please_wait), true);
        mProgressDialog.show();
    }

    private void hideProgress() {
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
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
        Logger.d(TAG, "showWrongUserInfo");
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.error_user_does_not_exist, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onBackKey() {
        Logger.d(TAG, "onBackKey");
        exitAnimation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Logger.d(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.login_menu, menu);
        mSearchMenuItem = menu.findItem(R.id.search);

        setupSearch((SearchView) MenuItemCompat.getActionView(mSearchMenuItem));

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupSearch(SearchView searchView) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d(TAG, "onOptionsItemSelected, item [" + item + "]");
        if(item.getItemId() == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        Logger.d(TAG, "onAttach");
        super.onAttach(activity);

        setSearchActivityListener(activity, this);
    }

    private void setSearchActivityListener(Activity activity, SearchListener listener) {
        if(activity instanceof SearchHolder) {
            ((SearchHolder) activity).setSearchListener(listener);
        } else {
            new IllegalStateException("Fragment shall be used with Activity which implements [" + SearchHolder.class + "]");
        }
    }

    @Override
    public void onDetach() {
        Logger.d(TAG, "onDetach");
        super.onDetach();
        setSearchActivityListener(getActivity(), null);
    }

    @Override
    public void search(String txt) {
        Logger.d(TAG, "search [" + txt + "]");
        mSearchMenuItem.collapseActionView();

        mPresenter.setUsername(txt);
        mPresenter.validateUserinfo();
    }

}
