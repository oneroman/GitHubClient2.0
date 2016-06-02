package com.roman.github.presenters;

import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;
import com.roman.github.data.RepositoryData;

import java.util.List;

/**
 * Created by Anna on 27.05.2016.
 */
public interface RepositoryDetail {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
    }

}
