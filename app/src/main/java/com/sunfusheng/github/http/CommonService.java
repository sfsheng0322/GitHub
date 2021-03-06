package com.sunfusheng.github.http;

import com.sunfusheng.github.model.Event;
import com.sunfusheng.github.model.Repo;
import com.sunfusheng.github.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author by sunfusheng on 2018/4/8.
 */
public interface CommonService {

    @GET("users/{username}")
    Observable<Response<User>> fetchUserDetail(
            @Path("username") String username
    );

    @GET("users/{username}/followers")
    Observable<Response<List<User>>> fetchFollowerList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("users/{username}/following")
    Observable<Response<List<User>>> fetchFollowingList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("users/{username}/repos")
    Observable<Response<List<Repo>>> fetchUserOwnedRepoList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Query("sort") String sort,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("users/{username}/starred")
    Observable<Response<List<Repo>>> fetchUserStarredRepoList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("users/{username}/events/public")
    Observable<Response<List<Event>>> fetchUserEvents(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("users/{username}/received_events")
    Observable<Response<List<Event>>> fetchReceivedEvents(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int pageCount,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET
    Observable<Response<Repo>> fetchRepoDetail(
            @Url String url,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );

    @GET("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> fetchStarred(
            @Path("username") String username,
            @Path("repo_name") String repo_name
    );

    @PUT("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> star(
            @Path("username") String username,
            @Path("repo_name") String repo_name
    );

    @DELETE("user/starred/{username}/{repo_name}")
    Observable<Response<Boolean>> unstar(
            @Path("username") String username,
            @Path("repo_name") String repo_name
    );

    @GET("/user/following/{username}")
    Observable<Response<Boolean>> fetchFollowed(
            @Path("username") String username
    );

    @PUT("/user/following/{username}")
    Observable<Response<Boolean>> follow(
            @Path("username") String username
    );

    @DELETE("/user/following/{username}")
    Observable<Response<Boolean>> unfollow(
            @Path("username") String username
    );

    @GET("repos/{repoFullName}/readme")
    @Headers("Accept: application/vnd.github.html")
    Observable<Response<ResponseBody>> fetchReadme(
            @Path(value = "repoFullName", encoded = true) String repoFullName,
            @Header("fetch-mode") int fetchMode,
            @Header("local-cache-validate-time") int localCacheValidateTime
    );
}
