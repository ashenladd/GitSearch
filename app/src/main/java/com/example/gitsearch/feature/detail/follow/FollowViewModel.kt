package com.example.gitsearch.feature.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.remote.mapper.toModel
import com.example.gitsearch.data.remote.network.ApiConfig
import com.example.gitsearch.data.remote.response.FollowerResponseItem
import com.example.gitsearch.data.remote.response.FollowingResponseItem
import com.example.gitsearch.feature.detail.follow.model.FollowModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _followerData = MutableLiveData<List<FollowModel>>()
    val followerData: LiveData<List<FollowModel>> = _followerData

    private val _followingData = MutableLiveData<List<FollowModel>>()
    val followingData: LiveData<List<FollowModel>> = _followingData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmptyFollowing = MutableLiveData<Boolean>()
    val isEmptyFollowing: LiveData<Boolean> = _isEmptyFollowing

    private val _isEmptyFollower = MutableLiveData<Boolean>()
    val isEmptyFollower: LiveData<Boolean> = _isEmptyFollower

    fun processEvent(event: FollowEvent) {
        when (event) {
            is FollowEvent.GetFollowers -> {
                getFollower(event.username)
            }

            is FollowEvent.GetFollowing -> {
                getFollowing(event.username)
            }
        }
    }

    private fun getFollower(username: String) {
        _isLoading.value = true
        _isEmptyFollower.value = false
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    _followerData.value = data?.map {
                        it.toModel()
                    }
                    _isEmptyFollower.value = _followerData.value?.isEmpty()
                }
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                Log.e(
                    "FollowViewModel",
                    "onFailure: ${t.message}"
                )
                _isLoading.value = false
                _isEmptyFollower.value = _followerData.value?.isEmpty()
            }

        })
    }

    private fun getFollowing(username: String) {
        _isLoading.value = true
        _isEmptyFollowing.value = false
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    _followingData.value = data?.map {
                        it.toModel()
                    }
                    _isEmptyFollowing.value = _followingData.value?.isEmpty()
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                Log.e(
                    "FollowViewModel",
                    "onFailure: ${t.message}"
                )
                _isLoading.value = false
                _isEmptyFollowing.value = _followingData.value?.isEmpty()
            }

        })
    }
}