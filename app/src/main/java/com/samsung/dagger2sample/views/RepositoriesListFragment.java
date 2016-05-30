package com.samsung.dagger2sample.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.samsung.dagger2sample.GitHubAPI.pojo.Repository;
import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;
import com.samsung.dagger2sample.adapters.RepositoryAdapter;
import com.samsung.dagger2sample.base.BaseFragment;
import com.samsung.dagger2sample.GitHubAPI.GitHubAPI;
import com.samsung.dagger2sample.MyApplication;
import com.samsung.dagger2sample.R;
import com.samsung.dagger2sample.presenters.RepositoriesList;
import com.samsung.dagger2sample.presenters.RepositoriesListPresenter;
import com.samsung.dagger2sample.utils.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoriesListFragment extends BaseFragment implements RepositoriesList.View {

    private static final String TAG = RepositoriesListFragment.class.getSimpleName();

    private static final String USERINFO = "userinfo";

    private Userinfo mUserinfo;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    private RepositoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Inject
    RepositoriesList.Presenter mPresenter;
    @Inject
    GitHubAPI mGitHubApi;
    @Inject
    ExecutorService mExecutor;

    @Override
    protected void setup() {
        ((MyApplication) getActivity().getApplication()).getRepositoriesListComponent().inject(this);

        mPresenter.setView(this);
        mPresenter.init(mGitHubApi, mExecutor);
    }

    public static RepositoriesListFragment newInstance(Userinfo userinfo) {
        RepositoriesListFragment fragment = new RepositoriesListFragment();
        Bundle args = new Bundle();
        args.putParcelable(USERINFO, userinfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserinfo = getArguments().getParcelable(USERINFO);
        }
        Logger.d(TAG, "onCreate, username [" + mUserinfo + "]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_repositories_list, container, false);
        ButterKnife.bind(this, view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RepositoryAdapter();

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getRepositories(mUserinfo.login);
    }

    @Override
    public void showLoading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showRepositories(List<Repository> list) {
        Logger.d(TAG, "showRepositories");
        mAdapter.addRespositories(list);
    }

    @Override
    public void appendRepository(Repository repo) {
        Logger.d(TAG, "appendRepository");
        mAdapter.addRespository(repo);
    }
}
