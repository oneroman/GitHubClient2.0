package com.roman.github.data.converter;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.GitHubAPI.pojo.Userinfo;
import com.roman.github.data.RepositoryData;
import com.roman.github.data.UserInfoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 16. 6. 2.
 */
final public class DataConverter {

    public static List<RepositoryData> convert(List<Repository> repositoriesListResponse) {
        if(repositoriesListResponse == null) {
            return null;
        }

        final List<RepositoryData> list = new ArrayList<>(repositoriesListResponse.size());
        for (Repository repositoryResponse : repositoriesListResponse) {
            list.add(convert(repositoryResponse));
        }
        return list;
    }

    public static RepositoryData convert(Repository repository) {
        if(repository == null) {
            return null;
        }
        return new RepositoryData(repository.name, repository.description, repository.homepage, repository.size, repository.language, repository.open_issues_count, repository.forks_count);
    }

    public static UserInfoData convert(Userinfo user) {
        if(user == null) {
            return null;
        }
        return new UserInfoData(user.login, user.name, user.avatar_url);
    }
}
