package com.roman.github.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by roman on 16. 6. 3.
 */
public class PageRecyclerView extends RecyclerView {

    private boolean mLoading = false;
    private boolean mLastPage = false;
    private PageLoaderListener mListener;

    public interface PageLoaderListener {
        void onLoadNextPage();
    }

    public PageRecyclerView(Context context) {
        super(context);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setListener(PageLoaderListener listener) {
        this.mListener = listener;
        scrollListener(listener == null ? null : mRecyclerViewOnScrollListener);
    }

    public void setLoading(final boolean value) {
        mLoading = value;
    }

    public void setLastPage(final boolean value) {
        mLastPage = value;
    }

    private void scrollListener(RecyclerView.OnScrollListener l) {
        this.addOnScrollListener(l);
    }

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            LayoutManager layoutManager = getLayoutManager();
            if(layoutManager instanceof LinearLayoutManager || layoutManager instanceof GridLayoutManager) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition;
                if(layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager lm = ((LinearLayoutManager) layoutManager);
                    firstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                } else {
                    GridLayoutManager lm = ((GridLayoutManager) layoutManager);
                    firstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                }

                if (!mLoading && !mLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        if (mListener != null) {
                            mListener.onLoadNextPage();
                        }
                    }
                }
            }
        }
    };
}
