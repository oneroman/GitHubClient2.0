package com.roman.github.presenters;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoryDetailPresenter implements RepositoryDetail.Presenter {

    private static final String TAG = RepositoryDetailPresenter.class.getSimpleName();

    private RepositoryDetail.View mView;

    public RepositoryDetailPresenter(RepositoryDetail.View view) {
        mView = view;
    }

    @Override
    public void start() {
    }

}
