package com.roman.github.presenters;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;

import java.util.List;

/**
 * Created by Anna on 27.05.2016.
 */
public interface RepositoriesList {

    interface View extends BaseView {
        void showLoading(boolean loading);
        void showRepositories(List<Repository> repos);
        void appendRepository(Repository repo);
    }

    interface Presenter extends BasePresenter<View> {
        void getRepositories(String repositoryName);
    }

}
