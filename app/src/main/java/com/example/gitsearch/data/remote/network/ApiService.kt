package com.example.gitsearch.data.remote.network

import com.example.gitsearch.data.remote.response.DetailResponse
import com.example.gitsearch.data.remote.response.FollowerResponseItem
import com.example.gitsearch.data.remote.response.FollowingResponseItem
import com.example.gitsearch.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
     @GET(Constant.SEARCH_USER)
     fun searchUser(
         @Query("q") query: String,
     ): Call<SearchResponse>

     @GET(Constant.USER_DETAIL)
     fun getUserDetail(
         @Path("username") username: String
     ): Call<DetailResponse>

     @GET(Constant.USER_FOLLOWERS)
     fun getUserFollowers(
         @Path("username") username: String,
     ): Call<List<FollowerResponseItem>>

     @GET(Constant.USER_FOLLOWING)
     fun getUserFollowing(
         @Path("username") username: String,
     ): Call<List<FollowingResponseItem>>
}