package com.samsung.dagger2sample.GitHubAPI;

import com.samsung.dagger2sample.GitHubAPI.pojo.Repository;

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

    public Observable<List<Repository>> getUsersRepositories(String user) {
        return githubApi.listRepositories(user)
                /*.map(new Func1<List<RepositoryResponse>, ImmutableList<Repository>>() {
                    @Override
                    public ImmutableList<Repository> call(List<RepositoryResponse> repositoriesListResponse) {
                        final ImmutableList.Builder<Repository> listBuilder = ImmutableList.builder();
                        for (RepositoryResponse repositoryResponse : repositoriesListResponse) {
                            Repository repository = new Repository();
                            repository.id = repositoryResponse.id;
                            repository.name = repositoryResponse.name;
                            repository.url = repositoryResponse.url;
                            listBuilder.add(repository);
                        }
                        return listBuilder.build();
                    }
                })*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
