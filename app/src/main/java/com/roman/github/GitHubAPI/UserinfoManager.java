package com.roman.github.GitHubAPI;

import com.roman.github.GitHubAPI.pojo.Userinfo;
import com.roman.github.data.UserInfoData;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Anna on 29.05.2016.
 */
public class UserinfoManager {

    private GitHubAPI githubApi;

    public UserinfoManager(GitHubAPI githubApi) {
        this.githubApi = githubApi;
    }

    public Observable<UserInfoData> getUserinfo(String name) {
        return githubApi.userInfo(name)
                .map(new Func1<Userinfo, UserInfoData>() {
                    @Override
                    public UserInfoData call(Userinfo userinfo) {
                        return new UserInfoData(userinfo.login, userinfo.name, userinfo.avatar_url);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
