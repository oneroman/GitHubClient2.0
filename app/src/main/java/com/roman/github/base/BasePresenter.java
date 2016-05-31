package com.roman.github.base;

/**
 * Created by Anna on 27.05.2016.
 */
public interface BasePresenter<T> {

    void setView(T view);
    void start();
}
