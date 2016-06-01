package com.roman.github.modules;

import com.roman.github.FragmentScope;
import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.presenters.UserLogin;
import com.roman.github.presenters.UserLoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class UserLoginModule {
    UserLogin.View view;

    public UserLoginModule(UserLogin.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    UserLogin.Presenter providesUserLoginPresenter(GitHubAPI gitHubAPI) {
        return new UserLoginPresenter(view, gitHubAPI);
    }
}
