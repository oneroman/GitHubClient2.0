package com.roman.github.presenters;

import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;
import com.roman.github.data.UserInfoData;

/**
 * Created by Anna on 27.05.2016.
 */
public interface UserLogin {

    interface View extends BaseView {
        void requestingUserinfo();
        void showUserinfo(UserInfoData userinfo);
        void wrongUsername();
        void noInternetConnection();
    }

    interface Presenter extends BasePresenter<View> {

        void setUsername(String value);

        void validateUserinfo();
    }

}
