package com.roman.github.views;

/**
 * Created by roman on 16. 6. 3.
 */
public interface SearchListener {
    void search(String txt);

    interface SearchHolder {
        void setSearchListener(SearchListener listener);
    }
}
