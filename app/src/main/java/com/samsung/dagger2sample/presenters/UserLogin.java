package com.samsung.dagger2sample.presenters;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;
import com.samsung.dagger2sample.base.BasePresenter;
import com.samsung.dagger2sample.base.BaseView;

import java.util.concurrent.ExecutorService;

/**
 * Created by Anna on 27.05.2016.
 */
public interface UserLogin {

    interface View extends BaseView {
        void requestingUserinfo();
        void showUserinfo(Userinfo userinfo);
    }

    interface Presenter extends BasePresenter<View> {
        void init(GitHubAPI gitHub);

        String getUsername();
        void setUsername(String value);

        void getUserinfo();
    }

}
