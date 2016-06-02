package com.roman.github.presenters;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.GitHubAPI.UserinfoManager;
import com.roman.github.MyApplication;
import com.roman.github.contentprovider.RecentSearchController;
import com.roman.github.data.UserInfoData;
import com.roman.github.utils.Logger;
import com.roman.github.utils.TextUtils;

import rx.Observer;

/**
 * Created by Anna on 27.05.2016.
 */
public class UserLoginPresenter implements UserLogin.Presenter {

    private static final String TAG = UserLoginPresenter.class.getSimpleName();

    private UserLogin.View mView;

    private String mUsername;
    private GitHubAPI mGitHub;
    private UserinfoManager mUserinfoManager;

    public UserLoginPresenter(UserLogin.View view, GitHubAPI gitHub) {
        mView = view;
        mGitHub = gitHub;
    }

    @Override
    public void start() {
    }

    @Override
    public void setUsername(String value) {
        mUsername = value;
    }

    private boolean validateUserName() {
        final boolean correct = !TextUtils.isEmpty(mUsername);
        if(!correct) {
            mView.wrongUsername();
        }
        return correct;
    }

    @Override
    public void validateUserinfo() {
        Logger.d(TAG, "validateUserinfo");
        if(!validateUserName()) {
            return;
        }
        RecentSearchController.remember(MyApplication.getAppContext(), mUsername);
        mView.requestingUserinfo();

        mUserinfoManager = new UserinfoManager(mGitHub);
        mUserinfoManager.getUserinfo(mUsername).subscribe(new Observer<UserInfoData>() {
            @Override
            public void onCompleted() {
                Logger.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d(TAG, "onError");
                mView.showUserinfo(null);
            }

            @Override
            public void onNext(UserInfoData userinfo) {
                Logger.d(TAG, "onNext [" + userinfo + "]");
                mView.showUserinfo(userinfo);
            }
        });
    }
}
