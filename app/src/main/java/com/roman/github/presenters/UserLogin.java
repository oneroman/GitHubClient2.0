package com.roman.github.presenters;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.GitHubAPI.pojo.Userinfo;
import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;

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
