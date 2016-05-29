package com.samsung.dagger2sample;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.presenters.RepositoriesList;
import com.samsung.dagger2sample.presenters.RepositoriesListPresenter;
import com.samsung.dagger2sample.presenters.UserLogin;
import com.samsung.dagger2sample.presenters.UserLoginPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anna on 27.05.2016.
 */
@Singleton
@Module
public class AppModule {

    @Provides
    @Singleton
    UserLogin.Presenter providesUserLoginPresenter() {
        return new UserLoginPresenter();
    }

    @Provides
    @Singleton
    RepositoriesList.Presenter providesRepositoriesListPresenter() {
        return new RepositoriesListPresenter();
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideHttpClient(Converter.Factory factory) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    GitHubAPI provideGitHubAPI(Retrofit retrofit) {
        return retrofit.create(GitHubAPI.class);
    }
}
