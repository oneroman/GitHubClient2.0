package com.roman.github.modules;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.presenters.RepositoriesList;
import com.roman.github.presenters.RepositoriesListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesListModule {
    RepositoriesList.View view;
    GitHubAPI gitHubAPI;

    public RepositoriesListModule(RepositoriesList.View view, GitHubAPI gitHub) {
        this.view = view;
        this.gitHubAPI = gitHub;
    }

    @Provides
    RepositoriesList.Presenter getRepositoriesListPresenter() {
        return new RepositoriesListPresenter(view, gitHubAPI);
    }
}
