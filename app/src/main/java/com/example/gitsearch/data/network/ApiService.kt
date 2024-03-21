package com.example.gitsearch.data.network

import com.example.gitsearch.data.response.DetailResponse
import com.example.gitsearch.data.response.FollowerResponse
import com.example.gitsearch.data.response.FollowingResponse
import com.example.gitsearch.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
     @GET(Constant.SEARCH_USER)
     suspend fun searchUser(
         @Query("q") query: String,
     ): Call<SearchResponse>

     @GET(Constant.USER_DETAIL)
     suspend fun getUserDetail(
         @Path("username") username: String
     ): Call<DetailResponse>

     @GET(Constant.USER_FOLLOWERS)
     suspend fun getUserFollowers(
         @Path("username") username: String,
     ): Call<FollowerResponse>

     @GET(Constant.USER_FOLLOWING)
     suspend fun getUserFollowing(
         @Path("username") username: String,
     ): Call<FollowingResponse>
}