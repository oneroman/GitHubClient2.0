package com.roman.github.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roman.github.R;
import com.roman.github.base.BaseFragment;
import com.roman.github.data.RepositoryData;
import com.roman.github.modules.RepositoryDetailModule;
import com.roman.github.presenters.RepositoryDetail;
import com.roman.github.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoryDetailFragment extends BaseFragment implements BackKeyListener, RepositoryDetail.View {

    private static final String TAG = RepositoryDetailFragment.class.getSimpleName();

    private static final String REPOSITORYINFO = "repository";

    private RepositoryData mRepository;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_details)
    TextView text_details;
    @BindView(R.id.text_home_page)
    TextView homePage;
    @BindView(R.id.language)
    TextView language;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.open_issues)
    TextView openIssues;
    @BindView(R.id.forks_count)
    TextView forksCount;

    private Unbinder unbinder;

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
        final View view = inflater.inflate(R.layout.fragment_repository_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        setActionBar();

        /*collapsing_toolbar.setTitle(mUserinfo == null ? getString(R.string.unknown_str) : (mUserinfo.getName() != null ? mUserinfo.getName() : mUserinfo.getLogin()));


        setList();*/

        toolbar.setTitle(mRepository.getName());
        if(mRepository.getDescription() == null || mRepository.getDescription().isEmpty()) {
            text_details.setText(R.string.description_absent);
        } else {
            text_details.setText(mRepository.getDescription());
        }

        String hp = mRepository.getHomePage();
        homePage.setVisibility(hp == null || hp.isEmpty() ? View.GONE : View.VISIBLE);
        homePage.setText(String.format(getResources().getString(R.string.description_home_page),hp));

        language.setText(String.format(getResources().getString(R.string.description_language), mRepository.getLanguage()));

        size.setText(String.format(getResources().getString(R.string.description_size), mRepository.getSize()));

        openIssues.setText(String.format(getResources().getString(R.string.description_open_issues), mRepository.getOpenIssues()));
        forksCount.setText(String.format(getResources().getString(R.string.description_forks_count), mRepository.getForksCount()));

        return view;
    }

    private void setActionBar() {
        showToolBarBackButton(toolbar);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        super.onViewCreated(view, savedInstanceState);

        if(mRepository != null) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();unbinder = null;
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackKey() {
        Logger.d(TAG, "onBackKey");
    }
}
