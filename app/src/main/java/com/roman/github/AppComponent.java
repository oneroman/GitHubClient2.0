package com.roman.github;

import com.roman.github.GitHubAPI.GitHubAPI;
import com.roman.github.pool.ThreadPoolModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Anna on 27.05.2016.
 */
@Singleton
@Component(modules={AppModule.class, ThreadPoolModule.class})
public interface AppComponent {
    GitHubAPI gitHubAPI();
    Picasso picasso();
}
