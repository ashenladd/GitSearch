package com.example.gitsearch.feature.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.remote.mapper.toModel
import com.example.gitsearch.data.remote.network.ApiConfig
import com.example.gitsearch.data.remote.response.DetailResponse
import com.example.gitsearch.feature.detail.model.DetailModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){
    private val _detailData = MutableLiveData<DetailModel>()
    val detailData : MutableLiveData<DetailModel> = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading

    fun processEvent(event: DetailEvent){
        when(event){
            is DetailEvent.GetDetailUser -> {
                getUserDetail(event.username)
            }
        }
    }
    private fun getUserDetail(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val data = response.body()
                    _detailData.value = data?.toModel()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message}")
                _isLoading.value = false
            }
        })
    }
}