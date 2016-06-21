package com.roman.github.settings;

import android.content.SharedPreferences;

import com.roman.github.utils.Logger;

/**
 * Created by roman on 16. 6. 21.
 */
public class DevelopersSettings {

    private static final String TAG = DevelopersSettings.class.getSimpleName();

    public static final String DEVELOPER_SETTINGS_FILE = "DevelopersSettings";

    private static final String DEVELOPER_SETTINGS_VIEW_REPOSITORIES = "ViewRepositories";

    private static final String DEVELOPER_SETTINGS_LOGGING_APP = "LoggingApp";
    private static final String DEVELOPER_SETTINGS_LOGGING_CANARY = "LoggingCanary";
    private static final String DEVELOPER_SETTINGS_LOGGING_PICASSO = "LoggingPicasso";

    public static BooleanItem APP_LOG = null;
    public static BooleanItem CANARY_LOG = null;
    public static BooleanItem PICASSO_LOG = null;

    private SharedPreferences mSharedPref;

    public DevelopersSettings(SharedPreferences pref) {
        mSharedPref = pref;
        loadLogging();
        for(ViewRepositories val : ViewRepositories.values()){
            val.setPref(mSharedPref);
        }
    }

    public enum ViewRepositories {
        LIST_VIEW(1) {
            @Override
            public void save() {
                LIST_VIEW.internalStore();
            }
        },
        GRID_VIEW(2) {
            @Override
            public void save() {
                GRID_VIEW.internalStore();
            }
        };

        private final int setting;
        private final String filename = DEVELOPER_SETTINGS_VIEW_REPOSITORIES;
        private SharedPreferences pref;

        ViewRepositories(int v) {
            this.setting = v;
        }

        public int getValue() {
            return setting;
        }

        String getSettingName() {
            return filename;
        }

        void setPref(SharedPreferences pref) {
            this.pref = pref;
        }

        public static ViewRepositories valueOf(int value) {
            switch(value){
                case 2:  return GRID_VIEW;
                default: return LIST_VIEW;
            }
        }

        private void internalStore() {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(filename, setting);
            editor.apply();
        }

        public abstract void save();
    }

    public ViewRepositories getViewRepository() {
        ViewRepositories result = ViewRepositories.valueOf(mSharedPref.getInt(ViewRepositories.LIST_VIEW.getSettingName(), ViewRepositories.LIST_VIEW.getValue()));
        Logger.d(TAG, "getViewRepository, result [" + result + "]");
        return result;
    }

    private void loadLogging() {
        APP_LOG = new BooleanItem(mSharedPref, DEVELOPER_SETTINGS_LOGGING_APP);
        CANARY_LOG = new BooleanItem(mSharedPref, DEVELOPER_SETTINGS_LOGGING_CANARY);
        PICASSO_LOG = new BooleanItem(mSharedPref, DEVELOPER_SETTINGS_LOGGING_PICASSO);
    }

    public static class BooleanItem {
        private boolean state;
        private String settingname;
        private SharedPreferences mPref;
        private static final boolean DEFAULT_VALUE = true;

        BooleanItem(SharedPreferences pref, String sname) {
            mPref = pref;
            settingname = sname;
            state = mPref.getBoolean(sname, DEFAULT_VALUE);
        }

        public void modifySetting() {
            state = !state;
            Logger.d(TAG, "modifySetting [" + settingname + "] for new value [" + state + "]");
            SharedPreferences.Editor editor = mPref.edit();
            editor.putBoolean(settingname, state);
            editor.apply();
        }

        public boolean getState() {
            return state;
        }
    }

}
