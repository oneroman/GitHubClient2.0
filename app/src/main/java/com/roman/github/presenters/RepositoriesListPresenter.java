package com.roman.github.presenters;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.GitHubAPI.RepositoriesManager;
import com.roman.github.cache.GitHubCache;
import com.roman.github.data.RepositoryData;
import com.roman.github.data.UserInfoData;
import com.roman.github.utils.Logger;

import java.util.List;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoriesListPresenter implements RepositoriesList.Presenter {

    private static final String TAG = RepositoriesListPresenter.class.getSimpleName();

    private RepositoriesList.View mView;

    GitHubAPI mGitHubApi;

    UserInfoData mUserInfo;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    RepositoriesManager mRepositoriesManager;
    private GitHubCache<List<RepositoryData>> repositoryCache;

    final private static int PAGE_SIZE = 10;
    private int totalItemsAlreadyDownloaded = 0;

    public RepositoriesListPresenter(RepositoriesList.View view, GitHubAPI gitHubApi) {
        mView = view;
        mGitHubApi = gitHubApi;
        mRepositoriesManager = new RepositoriesManager(mGitHubApi, PAGE_SIZE);
        repositoryCache = new GitHubCache();
    }

    @Override
    public void start() {
    }

    @Override
    public void setUserInfo(UserInfoData user) {
        mUserInfo = user;
    }

    @Override
    public void viewCreated() {
        Logger.d(TAG, "viewCreated");
        totalItemsAlreadyDownloaded = repositoryCache.get().size();
        if(totalItemsAlreadyDownloaded == 0) {
            Logger.d(TAG, "viewCreated, dont have cache");
            getRepositories();
        } else {
            Logger.d(TAG, "viewCreated, cache is available");
            mView.showRepositories(repositoryCache.get(), false);
        }
    }

    @Override
    public void getRepositories() {
        if(mUserInfo == null) return;

        String user = mUserInfo.getLogin();
        Logger.d(TAG, "starts getRepositories for user [" + user + "]");
        int nextPage;
        if(totalItemsAlreadyDownloaded == 0) {
            nextPage = 1;
        } else {
            nextPage = totalItemsAlreadyDownloaded / PAGE_SIZE + 1;
        }
        mView.showLoading(true);

        Logger.d(TAG, "starts getRepositories page [" + nextPage + "]");

        subscriptions.add(mRepositoriesManager.getAllUsersRepositories(user, nextPage).subscribe(new Observer<List<RepositoryData>>() {
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
            public void onNext(List<RepositoryData> items) {
                Logger.d(TAG, "onNext, repository [" + items + "], size [" + items.size() + "]");
                //lets hide as soon as got first item
                mView.showLoading(false);
                repositoryCache.add(items);
                totalItemsAlreadyDownloaded += items.size();
                mView.showRepositories(items, items.size() < PAGE_SIZE);
            }
        }));
        Logger.d(TAG, "ends getRepositories");
    }

    public void destroy() {
        Logger.d(TAG, "destroy");
        subscriptions.clear();
        repositoryCache.clear();
    }

}
