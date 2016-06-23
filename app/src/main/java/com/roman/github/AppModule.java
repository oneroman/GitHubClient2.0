package com.roman.github;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.roman.github.GitHubAPI.GitHubAPI;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anna on 27.05.2016.
 */
@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
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

        addGitHubInterceptor(builder);
        addStethoInterceptor(builder);

        Cache cache = new Cache(app.getCacheDir(), 2 * 1024 * 1024);//2 Mb
        builder.cache(cache);

        builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS);

        return builder.build();
    }

    private void addGitHubInterceptor(OkHttpClient.Builder builder) {
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("client_id", app.getResources().getString(R.string.client_id))
                        .addQueryParameter("client_secret", app.getResources().getString(R.string.client_secret))
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });
    }


    private void addStethoInterceptor(OkHttpClient.Builder builder) {
        builder.addInterceptor(new StethoInterceptor());
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

    @Provides
    @Singleton
    Picasso providePicasso(OkHttpClient okHttpClient) {
        Picasso picasso = new Picasso.Builder(app)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();

        if (BuildConfig.DEBUG) {
            picasso.setIndicatorsEnabled(true);
            picasso.setLoggingEnabled(true);
        }
        return picasso;
    }
}
