package com.roman.github.modules;

import com.roman.github.FragmentScope;
import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.presenters.RepositoriesList;
import com.roman.github.presenters.RepositoriesListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesListModule {
    RepositoriesList.View view;

    public RepositoriesListModule(RepositoriesList.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    RepositoriesList.Presenter getRepositoriesListPresenter(GitHubAPI gitHub) {
        return new RepositoriesListPresenter(view, gitHub);
    }
}
