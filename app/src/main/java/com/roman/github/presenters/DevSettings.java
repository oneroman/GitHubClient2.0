package com.roman.github.presenters;

import android.view.Menu;
import android.view.MenuItem;

import com.roman.github.base.BasePresenter;
import com.roman.github.base.BaseView;

/**
 * Created by roman on 16. 6. 21.
 */
public class DevSettings {

    interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

        void init(Menu menu);

        void select(MenuItem menuItem);
    }
}
