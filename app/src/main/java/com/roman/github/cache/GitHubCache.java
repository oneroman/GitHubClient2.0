package com.roman.github.cache;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by roman on 16. 6. 3.
 */
public class GitHubCache<T extends Collection> {

    List<T> cache = new LinkedList<>();

    public GitHubCache() {

    }

    public void add(T t) {
        cache.addAll(t);
    }

    public T get() {
        return (T) cache;
    }

    public void clear() {
        cache.clear();
    }
}
