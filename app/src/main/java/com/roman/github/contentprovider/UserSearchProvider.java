package com.roman.github.contentprovider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Anna on 02.06.2016.
 */
public class UserSearchProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.roman.github.UserSearchProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public UserSearchProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
