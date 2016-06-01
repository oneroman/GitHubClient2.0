package com.roman.github.components;

import com.roman.github.FragmentScope;
import com.roman.github.modules.RepositoriesListModule;
import com.roman.github.views.RepositoriesListFragment;

import dagger.Subcomponent;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Subcomponent(modules={RepositoriesListModule.class})
public interface RespositoriesListComponent {

    void inject(RepositoriesListFragment view);
}
