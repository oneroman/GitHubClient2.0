package com.roman.github.components;

import com.roman.github.FragmentScope;
import com.roman.github.modules.UserLoginModule;
import com.roman.github.views.UserLoginFragment;

import dagger.Subcomponent;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Subcomponent(modules={UserLoginModule.class})
public interface UserLoginComponent {
    void inject(UserLoginFragment view);

}
