package com.roman.github.components;

import com.roman.github.AppComponent;
import com.roman.github.FragmentScope;
import com.roman.github.modules.RepositoriesListModule;
import com.roman.github.views.RepositoriesListFragment;

import dagger.Component;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Component(modules={RepositoriesListModule.class}, dependencies={AppComponent.class})
public interface RespositoriesListComponent {

    void inject(RepositoriesListFragment view);
}
