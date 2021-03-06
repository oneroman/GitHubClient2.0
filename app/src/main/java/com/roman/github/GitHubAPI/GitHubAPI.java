package com.roman.github.GitHubAPI;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.GitHubAPI.pojo.Userinfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by roman on 16. 5. 27.
 */
public interface GitHubAPI {

    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepositories(@Path("user") String user);
    @GET("users/{user}/repos")
    Observable<List<Repository>> listRepositories(@Path("user") String user, @Query("page") int page, @Query("per_page") int count);
//    Call<List<Repository>> listRepositories(@Path("user") String user);

    @GET("users/{user}")
    Observable<Userinfo> userInfo(@Path("user") String user);
}
