package com.samsung.dagger2sample;

import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.presenters.RepositoriesList;
import com.samsung.dagger2sample.presenters.RepositoriesListPresenter;
import com.samsung.dagger2sample.presenters.UserLogin;
import com.samsung.dagger2sample.presenters.UserLoginPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    public OkHttpClient produceOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideHttpClient(Converter.Factory factory, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
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
