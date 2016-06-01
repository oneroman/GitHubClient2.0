package com.roman.github.GitHubAPI;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.data.RepositoryData;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Anna on 28.05.2016.
 */
public class RepositoriesManager {
    private GitHubAPI githubApi;

    public RepositoriesManager(GitHubAPI githubApi) {
        this.githubApi = githubApi;
    }

    public Observable<RepositoryData> getOneByOneUsersRepositories(String user) {
        return githubApi.listRepositories(user)
                .map(new Func1<List<Repository>, List<RepositoryData>>() {
                    @Override
                    public List<RepositoryData> call(List<Repository> repositoriesListResponse) {
                        final List<RepositoryData> list = new ArrayList<>(repositoriesListResponse.size());
                        for (Repository repositoryResponse : repositoriesListResponse) {
                            RepositoryData repository = new RepositoryData(repositoryResponse.name, repositoryResponse.description);
                            list.add(repository);
                        }
                        return list;
                    }
                })
                .flatMap(new Func1<List<RepositoryData>, Observable<RepositoryData>>() {
                    @Override
                    public Observable<RepositoryData> call(List<RepositoryData> repositories) {
                        return Observable.from(repositories);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Repository>> getAllUsersRepositories(String user) {
        return githubApi.listRepositories(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
