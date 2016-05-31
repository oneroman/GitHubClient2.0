package com.roman.github.presenters;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;
import com.roman.github.GitHubAPI.GitHubAPI;

import java.util.List;
import java.util.concurrent.ExecutorService;

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
        void init(GitHubAPI mGitHubApi, ExecutorService mExecutor );
        void getRepositories(String repositoryName);
    }

}
