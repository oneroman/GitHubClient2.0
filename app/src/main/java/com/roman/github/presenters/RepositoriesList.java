package com.roman.github.presenters;

import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;
import com.roman.github.data.RepositoryData;

import java.util.List;

/**
 * Created by Anna on 27.05.2016.
 */
public interface RepositoriesList {

    interface View extends BaseView {
        void showLoading(boolean loading);
        void showRepositories(List<RepositoryData> repos);
        void appendRepository(RepositoryData repo);
    }

    interface Presenter extends BasePresenter<View> {
        void getRepositories(String repositoryName);
    }

}
