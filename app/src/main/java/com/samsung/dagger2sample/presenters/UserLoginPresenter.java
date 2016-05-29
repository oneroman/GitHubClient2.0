package com.samsung.dagger2sample.presenters;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.GitHubAPI.UserinfoManager;
import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;
import com.samsung.dagger2sample.utils.Logger;

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

    @Override
    public void setView(UserLogin.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void init(GitHubAPI gitHub) {
        mGitHub = gitHub;
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
