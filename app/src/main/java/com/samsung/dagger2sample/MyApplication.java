package com.samsung.dagger2sample;

import android.app.Application;

import com.samsung.dagger2sample.pool.ThreadPoolModule;

/**
 * Created by roman on 16. 5. 27.
 */
public class MyApplication extends Application {

    private AppComponent mRepositoriesListComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mRepositoriesListComponent = DaggerAppComponent.builder().
                appModule(new AppModule())
                .threadPoolModule(new ThreadPoolModule())
                .build();

    }

    public AppComponent getRepositoriesListComponent() {
        return mRepositoriesListComponent;
    }

}
