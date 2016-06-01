package com.roman.github.modules;

import com.roman.github.FragmentScope;
import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.presenters.Splash;
import com.roman.github.presenters.SplashPresenter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {
    Splash.View view;

    public SplashModule(Splash.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    Splash.Presenter provideSplashPresenter(GitHubAPI gitHubAPI, Picasso picasso) {
        return new SplashPresenter(view);
    }
}
