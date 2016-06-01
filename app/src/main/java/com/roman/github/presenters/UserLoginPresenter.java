package com.roman.github.presenters;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.GitHubAPI.UserinfoManager;
import com.roman.github.GitHubAPI.pojo.Userinfo;
import com.roman.github.utils.Logger;

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
    public String getUsername() {
        return mUsername;
    }

    @Override
    public void setUsername(String value) {
        mUsername = value;
    }

    @Override
    public void getUserinfo() {
        Logger.d(TAG, "getUserinfo");
        mView.requestingUserinfo();

        mUserinfoManager = new UserinfoManager(mGitHub);
        mUserinfoManager.getUserinfo(mUsername).subscribe(new Observer<Userinfo>() {
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
            public void onNext(Userinfo userinfo) {
                Logger.d(TAG, "onNext [" + userinfo + "]");
                mView.showUserinfo(userinfo);
            }
        });
    }
}
