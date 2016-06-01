package com.roman.github.presenters;

import com.roman.github.GitHubAPI.pojo.Userinfo;
import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;

/**
 * Created by Anna on 27.05.2016.
 */
public interface UserLogin {

    interface View extends BaseView {
        void requestingUserinfo();
        void showUserinfo(Userinfo userinfo);
        void wrongUsername();
    }

    interface Presenter extends BasePresenter<View> {

        void setUsername(String value);

        void validateUserinfo();
    }

}
