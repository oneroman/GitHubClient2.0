package com.roman.github.contentprovider;

import android.content.Context;
import android.provider.SearchRecentSuggestions;

import com.roman.github.utils.Logger;
import com.roman.github.utils.TextUtils;

/**
 * Created by Anna on 02.06.2016.
 */
public class RecentSearchController {

    private static final String TAG = RecentSearchController.class.getSimpleName();

    public static void remember(Context context, String str) {
        if(context == null || TextUtils.isEmpty(str)) {
            return;
        }
        Logger.d(TAG, "remember [" + str + "]");
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(context, UserSearchProvider.AUTHORITY, UserSearchProvider.MODE);
        suggestions.saveRecentQuery(str, null);
    }
}
