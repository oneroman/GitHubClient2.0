package com.roman.github.modules;

import com.roman.github.FragmentScope;
import com.roman.github.presenters.RepositoryDetail;
import com.roman.github.presenters.RepositoryDetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryDetailModule {
    RepositoryDetail.View view;

    public RepositoryDetailModule(RepositoryDetail.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    RepositoryDetail.Presenter getRepositoryDetailPresenter() {
        return new RepositoryDetailPresenter(view);
    }
}
