package com.roman.github;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.roman.github.leakcanary.DisableLogger;
import com.roman.github.leakcanary.EnableLogger;
import com.roman.github.modules.SettingsModule;
import com.roman.github.pool.ThreadPoolModule;
import com.roman.github.presenters.DevSettings;
import com.roman.github.settings.DevelopersSettings;

import javax.inject.Inject;

/**
 * Created by roman on 16. 5. 27.
 */
public class MyApplication extends Application {

    private static MyApplication app;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        fixCanaryLeaks();

        setupStetho();
        setupLeak();
        setupAppComponent();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).mAppComponent;
    }

    public static Context getAppContext() {
        return app;
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void setupLeak() {
        com.squareup.leakcanary.LeakCanary.install(this);
        com.squareup.leakcanary.CanaryLog.setLogger(BuildConfig.DEBUG ? new EnableLogger() : new DisableLogger());
    }

    private void setupAppComponent() {
        mAppComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this))
                .threadPoolModule(new ThreadPoolModule())
                .settingsModule(new SettingsModule(this))
                .build();
    }

    private void fixCanaryLeaks() {
        fixClipboardUIManager();
    }

    private void fixClipboardUIManager() {
        if("samsung".equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            try {
                Class<?> cls = Class.forName("android.sec.clipboard.ClipboardUIManager");
                java.lang.reflect.Method m = cls.getDeclaredMethod("getInstance", Context.class);
                m.invoke(null, this);
            } catch (Exception ignored) { }
        }
    }

}
