package com.roman.github.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roman.github.R;
import com.roman.github.base.BaseFragment;
import com.roman.github.data.RepositoryData;
import com.roman.github.modules.RepositoryDetailModule;
import com.roman.github.presenters.RepositoryDetail;
import com.roman.github.utils.Logger;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoryDetailFragment extends BaseFragment implements RepositoryDetail.View {

    private static final String TAG = RepositoryDetailFragment.class.getSimpleName();

    private static final String REPOSITORYINFO = "repository";

    private RepositoryData mRepository;

    @Inject
    RepositoryDetail.Presenter mPresenter;

    @Override
    protected void setupDI() {
        getAppComponent().plus(new RepositoryDetailModule(this)).inject(this);
    }

    public static RepositoryDetailFragment newInstance(RepositoryData repository) {
        RepositoryDetailFragment fragment = new RepositoryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(REPOSITORYINFO, repository);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepository = getArguments().getParcelable(REPOSITORYINFO);
        }
        Logger.d(TAG, "onCreate, repository [" + mRepository + "]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_repositories_list, container, false);
        ButterKnife.bind(this, view);

/*        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        collapsing_toolbar.setTitle(mUserinfo == null ? getString(R.string.unknown_str) : (mUserinfo.getName() != null ? mUserinfo.getName() : mUserinfo.getLogin()));


        setList();*/

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        super.onViewCreated(view, savedInstanceState);

        if(mRepository != null) {

        }
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

}