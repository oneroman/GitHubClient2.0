package com.roman.github.modules;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.presenters.UserLogin;
import com.roman.github.presenters.UserLoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class UserLoginModule {
    UserLogin.View view;
    GitHubAPI mGitHubAPI;

    public UserLoginModule(UserLogin.View view, GitHubAPI gitHubAPI) {
        this.view = view;
        mGitHubAPI = gitHubAPI;
    }

    @Provides
    UserLogin.Presenter providesUserLoginPresenter() {
        return new UserLoginPresenter(view, mGitHubAPI);
    }
}
