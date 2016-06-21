package com.roman.github.views;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.roman.github.data.RepositoryData;
import com.roman.github.data.UserInfoData;
import com.roman.github.modules.RepositoriesListModule;
import com.roman.github.adapters.RepositoryAdapter;
import com.roman.github.base.BaseFragment;
import com.roman.github.R;
import com.roman.github.presenters.RepositoriesList;
import com.roman.github.settings.DevelopersSettings;
import com.roman.github.utils.ActivityUtils;
import com.roman.github.utils.Logger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Anna on 27.05.2016.
 */
public class RepositoriesListFragment extends BaseFragment implements BackKeyListener, RepositoriesList.View {

    private static final String TAG = RepositoriesListFragment.class.getSimpleName();

    private static final String USERINFO = "userinfo";

    private UserInfoData mUserinfo;

    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    PageRecyclerView recyclerView;
    private RepositoryAdapter mAdapter;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.header)
    ImageView headerImage;

    private Unbinder unbinder;

    @Inject
    RepositoriesList.Presenter mPresenter;
    @Inject
    Picasso mPicasso;
    @Inject
    DevelopersSettings mDeveloperSettings;

    @Override
    protected void setupDI() {
        getAppComponent().plus(new RepositoriesListModule(this)).inject(this);
    }

    public static RepositoriesListFragment newInstance(UserInfoData userinfo) {
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
            mPresenter.setUserInfo(mUserinfo);
        }
        Logger.d(TAG, "onCreate, username [" + mUserinfo + "]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_repositories_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        setActionBar();

        collapsing_toolbar.setTitle(mUserinfo == null ? getString(R.string.unknown_str) : (mUserinfo.getName() != null ? mUserinfo.getName() : mUserinfo.getLogin()));

        if(savedInstanceState == null/* && animate == true*/) {
            //animate = false;
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    startInitAnimation();
                    return true;
                }
            });
        }

        setList();

        return view;
    }

    private void setActionBar() {
        showToolBarBackButton(toolbar);
    }

    private void setList() {
        // use a linear layout manager
        recyclerView.setLayoutManager(getRecyclerLayout());
        recyclerView.setListener(new PageRecyclerView.PageLoaderListener() {
            @Override
            public void onLoadNextPage() {
                Logger.d(TAG, "onLoadNextPage");
                requestRepositories();
            }
        });

        mAdapter = new RepositoryAdapter(mItemClickListener);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setAdapter(mAdapter);
    }

    private RepositoryAdapter.OnItemClickListener mItemClickListener = new RepositoryAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(RepositoryData item) {
            Logger.d(TAG, "onItemClick [" + item + "]");
            ActivityUtils.replaceFragment(getActivity().getSupportFragmentManager(), RepositoryDetailFragment.newInstance(item), R.id.fragment_container, RepositoryDetailFragment.class.getName());
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        super.onViewCreated(view, savedInstanceState);

        mPresenter.viewCreated();
        requestImage();
    }

    private void requestRepositories() {
        mPresenter.getRepositories();
    }

    private void requestImage() {
        downloadImage(headerImage, mUserinfo.getAvatarUrl());
    }

    private void startInitAnimation() {

        //move toolbar out of screen
        appBar.setTranslationY(-appBar.getHeight());

        //animate it
        appBar.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .setStartDelay(100)
                .start();
    }

    @Override
    public void onDestroyView() {
        Logger.d(TAG, "onDestroyView");
        super.onDestroyView();
        mAdapter = null;
        recyclerView.setListener(null);
        mPresenter.destroyView();
        unbinder.unbind();unbinder = null;
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showLoading(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        recyclerView.setLoading(loading);
    }

    @Override
    public void showRepositories(List<RepositoryData> list, boolean last) {
        Logger.d(TAG, "showRepositories, last [" + last+ "]");
        mAdapter.addRespositories(list);
        recyclerView.setLastPage(last);
    }

    @Override
    public void appendRepository(RepositoryData repo, boolean last) {
        Logger.d(TAG, "appendRepository, last [" + last+ "]");
        mAdapter.addRespository(repo);
        recyclerView.setLastPage(last);
    }

    private void downloadImage(final ImageView img, String url) {
        Logger.d(TAG, "downloadImage, url [" + url + "]");
        mPicasso.load(url).into(img, new Callback.EmptyCallback() {
            @Override public void onSuccess() {
                Logger.d(TAG, "image downloaded");
                updateViewsColors(img);
            }

            @Override public void onError() {
                Logger.e(TAG, "image not downloaded");
            }
        });
    }

    private void updateViewsColors(ImageView img) {
        Bitmap bitmap = null;
        if(img != null) {
            Drawable drawable = img.getDrawable();
            if(drawable != null && drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            }
        }
        if(bitmap != null && !bitmap.isRecycled()) {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    Logger.d(TAG, "onGenerated, palette [" + palette + "]");

                    int colorVibrantDefaultInt = getResources().getColor(R.color.colorPrimary);
                    int colorVibrantInt = palette.getVibrantColor(colorVibrantDefaultInt);
                    Logger.d(TAG, "onGenerated, colorVibrantInt [" + colorVibrantInt + "], default [" + colorVibrantDefaultInt + "]");

                    int colorMutedDefaultInt = getResources().getColor(android.R.color.white);
                    int colorMutedInt = palette.getMutedColor(colorMutedDefaultInt);
                    Logger.d(TAG, "onGenerated, colorMutedInt [" + colorMutedInt + "], default [" + colorMutedDefaultInt + "]");

                    int colorDarkMutedDefaultInt = getResources().getColor(R.color.colorPrimary);
                    int colorDarkMutedInt = palette.getDarkMutedColor(colorDarkMutedDefaultInt);
                    Logger.d(TAG, "onGenerated, colorDarkMutedInt [" + colorDarkMutedInt + "], default [" + colorDarkMutedDefaultInt + "]");

                    collapsing_toolbar.setContentScrimColor(colorVibrantInt);
                    collapsing_toolbar.setCollapsedTitleTextColor(colorMutedInt);
                    collapsing_toolbar.setExpandedTitleColor(colorDarkMutedInt);
                }
            });
        }
    }

    private RecyclerView.LayoutManager getRecyclerLayout() {
        switch(mDeveloperSettings.getViewRepository()){
            case STAGGERED_GRID_VIEW:
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            case GRID_VIEW:
                return new GridLayoutManager(getContext(), 2);
            default:
                return new LinearLayoutManager(getContext());
        }
    }

    @Override
    public void onBackKey() {
        Logger.d(TAG, "onBackKey");
    }
}
