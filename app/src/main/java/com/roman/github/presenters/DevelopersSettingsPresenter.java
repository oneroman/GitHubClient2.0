package com.roman.github.presenters;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roman.github.R;
import com.roman.github.settings.DevelopersSettings;
import com.roman.github.utils.Logger;

import butterknife.ButterKnife;

/**
 * Created by roman on 16. 6. 21.
 */
public class DevelopersSettingsPresenter implements DevSettings.Presenter {
    private static final String TAG = DevelopersSettingsPresenter.class.getSimpleName();

    private DevelopersSettings mDevelopersSettings;

    private SparseArray<RepositoryViewItem> mapRepositoryView = new SparseArray<>();

    private LoggingMenuListener mLoggingApp;
    private LoggingMenuListener mLoggingLeakCanary;
    private LoggingMenuListener mLoggingLeakPicasso;

    public DevelopersSettingsPresenter(DevelopersSettings dev) {
        mDevelopersSettings = dev;
    }

    @Override
    public void init(Menu menu) {
        Logger.d(TAG, "init [" + menu + "]");

        //1. init Repository view menus <-- based on Enum
        mapRepositoryView.clear();
        RepositoryViewItem mRepositoryViewListItem = new RepositoryViewItem(menu.findItem(R.id.nav_repository_list), DevelopersSettings.ViewRepositories.LIST_VIEW);
        mapRepositoryView.put(mRepositoryViewListItem.getMenuItem().getItemId(), mRepositoryViewListItem);

        RepositoryViewItem  mRepositoryViewGridItem = new RepositoryViewItem(menu.findItem(R.id.nav_repository_grid), DevelopersSettings.ViewRepositories.GRID_VIEW);
        mapRepositoryView.put(mRepositoryViewGridItem.getMenuItem().getItemId(), mRepositoryViewGridItem);
        updateRepositorySetting();

        //2. init Loggers menus
        mLoggingApp = new LoggingMenuListener(menu.findItem(R.id.nav_logging_app), DevelopersSettings.APP_LOG);
        mLoggingLeakCanary = new LoggingMenuListener(menu.findItem(R.id.nav_logging_canary), DevelopersSettings.CANARY_LOG);
        mLoggingLeakPicasso = new LoggingMenuListener(menu.findItem(R.id.nav_logging_picasso), DevelopersSettings.PICASSO_LOG);


        /*MenuItem menuItem = menu.findItem(R.id.nav_first_menu);
        View actionView = MenuItemCompat.getActionView(menuItem);
        SwitchCompat switchFirstMenu = ButterKnife.findById(actionView, R.id.menu_switch);
        switchFirstMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "switchFirstMenu :: onClick");
            }
        });*/
    }

    @Override
    public void select(MenuItem menuItem) {
        Logger.d(TAG, "select [" + menuItem + "]");
        switch(menuItem.getItemId()) {
            case R.id.nav_first_menu:
                break;
            case R.id.nav_repository_list:
            case R.id.nav_repository_grid:
                mapRepositoryView.get(menuItem.getItemId()).pressed();
                updateRepositorySetting();
                break;
        }
    }

    @Override
    public void start() {

    }

    private void updateRepositorySetting() {
        Logger.d(TAG, "updateRepositorySetting");
        DevelopersSettings.ViewRepositories view = mDevelopersSettings.getViewRepository();
        for(int i = 0 ; i < mapRepositoryView.size(); i++) {
            mapRepositoryView.valueAt(i).getMenuItem().setChecked(mapRepositoryView.valueAt(i).getView() == view);
        }
    }

    static class RepositoryViewItem {
        MenuItem menu;
        DevelopersSettings.ViewRepositories item;
        RepositoryViewItem(MenuItem menu, DevelopersSettings.ViewRepositories item) {
            this.menu = menu;
            this.item = item;
        }

        void pressed() {
            item.save();
        }

        MenuItem getMenuItem() {
            return menu;
        }

        DevelopersSettings.ViewRepositories getView() {
            return item;
        }

    }

    private static class LoggingMenuListener {
        LoggingMenuListener(final MenuItem menu, final DevelopersSettings.BooleanItem logItem) {

            View actionView = MenuItemCompat.getActionView(menu);
            final AppCompatCheckBox checkBox = ButterKnife.findById(actionView, R.id.menu_checkbox);
            checkBox.setChecked(logItem.getState());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d(TAG, "mLoggingApp :: onClick");
                    logItem.modifySetting();
                    checkBox.setChecked(logItem.getState());
                }
            });
        }
    }

}
