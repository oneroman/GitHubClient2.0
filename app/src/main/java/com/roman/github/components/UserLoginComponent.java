package com.roman.github.components;

import com.roman.github.AppComponent;
import com.roman.github.FragmentScope;
import com.roman.github.modules.UserLoginModule;
import com.roman.github.views.UserLoginFragment;

import dagger.Component;

/**
 * Created by roman on 16. 6. 1.
 */
@FragmentScope
@Component(modules={UserLoginModule.class}, dependencies={AppComponent.class})
public interface UserLoginComponent {
    void inject(UserLoginFragment view);

}
