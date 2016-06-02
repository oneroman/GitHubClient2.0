package com.roman.github;

import android.app.Application;
import android.content.Context;

import com.roman.github.pool.ThreadPoolModule;

/**
 * Created by roman on 16. 5. 27.
 */
public class MyApplication extends Application {

    private static MyApplication app;
    private AppComponent mRepositoriesListComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        setupLeak();
        setupAppComponent();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).mRepositoriesListComponent;
    }

    public static Context getAppContext() {
        return app;
    }

    private void setupLeak() {
        com.squareup.leakcanary.LeakCanary.install(this);
    }

    private void setupAppComponent() {
        mRepositoriesListComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this))
                .threadPoolModule(new ThreadPoolModule())
                .build();
    }

}
