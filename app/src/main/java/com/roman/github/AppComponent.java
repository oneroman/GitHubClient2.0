package com.roman.github;

import com.roman.github.components.RespositoriesListComponent;
import com.roman.github.components.UserLoginComponent;
import com.roman.github.modules.RepositoriesListModule;
import com.roman.github.modules.UserLoginModule;
import com.roman.github.pool.ThreadPoolModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Anna on 27.05.2016.
 */
@Singleton
@Component(modules={AppModule.class, ThreadPoolModule.class})
public interface AppComponent {

    RespositoriesListComponent plus(RepositoriesListModule module);
    UserLoginComponent plus(UserLoginModule module);

}
