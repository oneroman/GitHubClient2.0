package com.samsung.dagger2sample.GitHubAPI;

import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Anna on 29.05.2016.
 */
public class UserinfoManager {

    private GitHubAPI githubApi;

    public UserinfoManager(GitHubAPI githubApi) {
        this.githubApi = githubApi;
    }

    public Observable<Userinfo> getUserinfo(String name) {
        return githubApi.userInfo(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
