package com.roman.github;

import android.app.Application;
import android.content.Context;

import com.roman.github.pool.ThreadPoolModule;

/**
 * Created by roman on 16. 5. 27.
 */
public class MyApplication extends Application {

    private AppComponent mRepositoriesListComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        com.squareup.leakcanary.LeakCanary.install(this);

        mRepositoriesListComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this))
                .threadPoolModule(new ThreadPoolModule())
                .build();

    }

    public static AppComponent getAppComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).mRepositoriesListComponent;
    }

}
