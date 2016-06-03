package com.roman.github.base;

import android.app.SearchManager;
import android.content.Intent;

import com.roman.github.utils.Logger;
import com.roman.github.views.SearchListener;

/**
 * Created by roman on 16. 6. 3.
 */
abstract public class SearchableActivity extends BaseActivity implements SearchListener.SearchHolder {

    private static final String TAG = SearchableActivity.class.getSimpleName();

    private SearchListener mSearchListener;

    @Override
    public void setSearchListener(SearchListener listener) {
        Logger.d(TAG, "setSearchListener [" + listener + "]");
        mSearchListener = listener;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Logger.d(TAG, "handleIntent [" + intent + "]");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            String query = intent.getStringExtra(SearchManager.QUERY);
            Logger.d(TAG, "ACTION_SEARCH for [" + query + "]");
            if(mSearchListener != null) {
                mSearchListener.search(query);
            }
        }
    }
}
