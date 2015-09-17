package be.appfoundry.mosbyrx.data.service;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;
import be.appfoundry.mosbyrx.data.entity.GitHubRepo;

/**
 * Created by janvancoppenolle on 31/08/15.
 */
public interface GitHubAPI {
    String URI = "https://api.github.com";

    // Both Synchronous and Asynchronous
    @GET("/users/google/repos")
    Call<List<GitHubRepo>> getRepos();

    // Rx
    @GET("/users/google/repos")
    Observable<List<GitHubRepo>> getReposRx();
}
