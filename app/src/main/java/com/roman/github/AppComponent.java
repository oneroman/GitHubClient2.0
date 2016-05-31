package com.roman.github;

import com.roman.github.pool.ThreadPoolModule;
import com.roman.github.views.RepositoriesListFragment;
import com.roman.github.views.UserLoginFragment;

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
