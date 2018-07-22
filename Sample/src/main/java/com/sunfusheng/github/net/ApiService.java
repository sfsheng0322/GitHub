package com.sunfusheng.github.net;

import com.sunfusheng.github.Constants;
import com.sunfusheng.github.model.Auth;
import com.sunfusheng.github.model.Event;
import com.sunfusheng.github.model.Repo;
import com.sunfusheng.github.model.User;
import com.sunfusheng.github.model.params.AuthParams;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author by sunfusheng on 2018/4/8.
 */
public interface ApiService {

    @GET("user")
    Observable<User> login();

    @POST("authorizations")
    Observable<Auth> createAuth(@Body AuthParams authParams);

    @Headers("Cache-Control: public, max-age=" + Constants.USER_MAX_AGE)
    @GET("users/{username}")
    Observable<Response<User>> fetchUser(@Path("username") String username);

    @Headers("Cache-Control: public, max-age=" + Constants.REPO_MAX_AGE)
    @GET("users/{username}/repos")
    Observable<Response<List<Repo>>> fetchRepos(@Path("username") String username,
                                                @Query("page") int page,
                                                @Query("per_page") int per_page,
                                                @Query("sort") String sort);

    @GET("users/{username}/events")
    Observable<Response<List<Event>>> fetchEvents(@Path("username") String username,
                                                  @Query("page") int page,
                                                  @Query("per_page") int per_page);

    @Headers("Cache-Control: public, max-age=" + Constants.RECEIVED_EVENTS_MAX_AGE)
    @GET("users/{username}/received_events")
    Observable<Response<List<Event>>> fetchReceivedEvents(@Path("username") String username,
                                                          @Query("page") int page,
                                                          @Query("per_page") int per_page);

    @Headers("Cache-Control: public, max-age=" + Constants.REPO_MAX_AGE)
    @GET
    Observable<Response<Repo>> fetchRepo(@Url String url);

    @GET("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> fetchStarred(@Path("username") String username, @Path("repo_name") String repo_name);

    @PUT("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> star(@Path("username") String username, @Path("repo_name") String repo_name);

    @DELETE("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> unstar(@Path("username") String username, @Path("repo_name") String repo_name);
}