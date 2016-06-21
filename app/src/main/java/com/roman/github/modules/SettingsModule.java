package com.roman.github.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.roman.github.presenters.DevSettings;
import com.roman.github.presenters.DevelopersSettingsPresenter;
import com.roman.github.settings.DevelopersSettings;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 16. 6. 21.
 */
@Module
public class SettingsModule {

    private Context mContext;

    public SettingsModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    @Named("developers.pref")
    public SharedPreferences provideAppSharedPref() {
        return mContext.getSharedPreferences(DevelopersSettings.DEVELOPER_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public DevelopersSettings provideDevelopersPref(@Named("developers.pref") SharedPreferences pref) {
        return new DevelopersSettings(pref);
    }

    @Provides
    @Singleton
    public DevSettings.Presenter provideSettingsPresenter(DevelopersSettings devSettings) {
        return new DevelopersSettingsPresenter(devSettings);
    }
}
