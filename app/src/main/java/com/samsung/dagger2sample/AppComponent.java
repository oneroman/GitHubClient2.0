package com.samsung.dagger2sample;

import com.samsung.dagger2sample.pool.ThreadPoolModule;
import com.samsung.dagger2sample.views.RepositoriesListFragment;
import com.samsung.dagger2sample.views.UserLoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Anna on 27.05.2016.
 */

@Singleton
@Component(modules={AppModule.class, ThreadPoolModule.class})
public interface AppComponent {
    void inject(RepositoriesListFragment view);
    void inject(UserLoginFragment view);
}
