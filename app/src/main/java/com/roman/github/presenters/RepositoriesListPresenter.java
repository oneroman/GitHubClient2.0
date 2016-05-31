package com.roman.github.presenters;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.GitHubAPI.RepositoriesManager;
import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoriesListPresenter implements RepositoriesList.Presenter {

    private static final String TAG = RepositoriesListPresenter.class.getSimpleName();

    private RepositoriesList.View mView;

    GitHubAPI mGitHubApi;

    ExecutorService mExecutor;

    private CompositeSubscription subscriptions = new CompositeSubscription();

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
        mRepositoriesManager = new RepositoriesManager(mGitHubApi);
    }

    @Override
    public void getRepositories(String username) {
        Logger.d(TAG, "starts getRepositories for user [" + username + "]");
        mView.showLoading(true);

        subscriptions.add(mRepositoriesManager.getOneByOneUsersRepositories(username).subscribe(new Observer<Repository>() {
            @Override
            public void onCompleted() {
                Logger.d(TAG, "onCompleted");
                mView.showLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                Logger.d(TAG, "onError");
                mView.showLoading(false);
            }

            @Override
            public void onNext(Repository repository) {
                Logger.d(TAG, "onNext, repository [" + repository + "]");
                //lets hide as soon as got first item
                mView.showLoading(false);
                mView.appendRepository(repository);
            }
        }));
        Logger.d(TAG, "ends getRepositories");
    }

    public void destroy() {
        Logger.d(TAG, "destroy");
        subscriptions.clear();
    }

}
