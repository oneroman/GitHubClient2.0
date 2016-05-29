package com.samsung.dagger2sample.presenters;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.GitHubAPI.RepositoriesManager;
import com.samsung.dagger2sample.GitHubAPI.pojo.Repository;
import com.samsung.dagger2sample.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

import rx.Observer;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoriesListPresenter implements RepositoriesList.Presenter {

    private static final String TAG = RepositoriesListPresenter.class.getSimpleName();

    private RepositoriesList.View mView;

    GitHubAPI mGitHubApi;

    ExecutorService mExecutor;

    RepositoriesManager mRepositoriesManager;

    @Override
    public void setView(RepositoriesList.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void init(GitHubAPI gitHubApi, ExecutorService executor) {
        mGitHubApi = gitHubApi;
        mExecutor = executor;
    }

    @Override
    public void getRepositories(String username) {
        Logger.d(TAG, "starts getRepositories for user [" + username + "]");
        mView.showLoading(true);
        mRepositoriesManager = new RepositoriesManager(username, mGitHubApi);
        mRepositoriesManager.getUsersRepositories().subscribe(new Observer<List<Repository>>() {
            @Override
            public void onCompleted() {
                Logger.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d(TAG, "onError");
                mView.showLoading(false);
            }

            @Override
            public void onNext(List<Repository> repositories) {
                Logger.d(TAG, "onNext, repositories [" + repositories + "]");

                mView.showLoading(false);
                mView.showRepositories(repositories);
            }
        });
        Logger.d(TAG, "ends getRepositories");
    }
}
