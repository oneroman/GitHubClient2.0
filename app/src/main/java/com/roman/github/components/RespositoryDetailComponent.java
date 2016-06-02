package com.roman.github.components;

import com.roman.github.FragmentScope;
import com.roman.github.modules.RepositoryDetailModule;
import com.roman.github.views.RepositoryDetailFragment;

import dagger.Subcomponent;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Subcomponent(modules={RepositoryDetailModule.class})
public interface RespositoryDetailComponent {

    void inject(RepositoryDetailFragment view);
}
