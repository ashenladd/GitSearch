package com.example.gitsearch.feature.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.remote.mapper.toModel
import com.example.gitsearch.data.remote.network.ApiConfig
import com.example.gitsearch.data.remote.response.SearchResponse
import com.example.gitsearch.feature.search.model.SearchModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _listSearch = MutableLiveData<List<SearchModel>>()
    val listSearch: LiveData<List<SearchModel>> = _listSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        _isEmpty.value = true

    }

    fun processEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchUser -> {
                searchUser(event.query)
            }
        }
    }

    private fun searchUser(query: String) {
        _isLoading.value = true
        _isEmpty.value = false
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()
                    _listSearch.value = data?.items?.map {
                        it?.toModel() ?: SearchModel(
                            login = "",
                            id = 0,
                            avatarUrl = ""
                        )
                    }
                    _isEmpty.value = _listSearch.value?.isEmpty()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(
                    "SearchViewModel",
                    "onFailure: ${t.message}"
                )
                _isLoading.value = false
                _isEmpty.value = _listSearch.value?.isEmpty()
            }
        })

    }
}