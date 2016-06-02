package com.roman.github.components;

import com.roman.github.FragmentScope;
import com.roman.github.modules.SplashModule;
import com.roman.github.views.SplashFragment;

import dagger.Subcomponent;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Subcomponent(modules={SplashModule.class})
public interface SplashComponent {
    void inject(SplashFragment view);

}
