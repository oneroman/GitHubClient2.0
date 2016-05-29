package com.samsung.dagger2sample.GitHubAPI;

import com.samsung.dagger2sample.GitHubAPI.pojo.Repository;
import com.samsung.dagger2sample.GitHubAPI.pojo.Userinfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by roman on 16. 5. 27.
 */
public interface GitHubAPI {

    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepositories(@Path("user") String user);
//    Call<List<Repository>> listRepositories(@Path("user") String user);

    @GET("users/{user}")
    Observable<Userinfo> userInfo(@Path("user") String user);
}
