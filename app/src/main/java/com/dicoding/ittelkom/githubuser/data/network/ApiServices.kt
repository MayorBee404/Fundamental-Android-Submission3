package com.dicoding.ittelkom.githubuser.data.network

import com.dicoding.ittelkom.githubuser.BuildConfig
import com.dicoding.ittelkom.githubuser.model.UserResponse
import com.dicoding.ittelkom.githubuser.model.SearchUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("users/{username}")
    @Headers("Authorization:  token ${BuildConfig.KEY}")
    fun getDetailUser(
        @Path("username")
        login: String
    ): Call<UserResponse>

    @GET("search/users")
    @Headers("Authorization:  token ${BuildConfig.KEY}")

    fun searchUsers (
        @Query("q")
        query: String
    ): Call<SearchUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization:  token ${BuildConfig.KEY}")

    fun getUserFollowers (
        @Path("username")
        login: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization:  token ${BuildConfig.KEY}")

    fun getUserFollowing (
        @Path("username")
        login: String
    ): Call<List<UserResponse>>


}
