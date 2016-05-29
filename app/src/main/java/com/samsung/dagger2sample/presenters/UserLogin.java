package com.samsung.dagger2sample.presenters;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.base.BasePresenter;
import com.samsung.dagger2sample.base.BaseView;

import java.util.concurrent.ExecutorService;

/**
 * Created by Anna on 27.05.2016.
 */
public interface UserLogin {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        String getUsername();
        void setUsername(String value);
    }

}
