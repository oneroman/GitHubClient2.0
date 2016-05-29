package com.samsung.dagger2sample.presenters;

import com.samsung.dagger2sample.GitHubAPI.pojo.Repository;
import com.samsung.dagger2sample.base.BasePresenter;
import com.samsung.dagger2sample.base.BaseView;
import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Anna on 27.05.2016.
 */
public interface RepositoriesList {

    interface View extends BaseView {
        void showLoading(boolean loading);
        void showRepositories(List<Repository> repos);
    }

    interface Presenter extends BasePresenter<View> {
        void init(GitHubAPI mGitHubApi, ExecutorService mExecutor );
        void getRepositories(String repositoryName);
    }

}
