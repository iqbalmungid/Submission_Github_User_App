package com.iqbalmungid.githubuserapi.data.remote.api

import com.iqbalmungid.githubuserapi.BuildConfig
import com.iqbalmungid.githubuserapi.data.remote.response.DetailUserResponse
import com.iqbalmungid.githubuserapi.data.remote.response.User
import com.iqbalmungid.githubuserapi.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}