package com.example.gitsearch.feature.detail

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.Result
import com.example.gitsearch.data.local.entity.FavoriteUser
import com.example.gitsearch.data.repository.UserRepository
import com.example.gitsearch.feature.detail.model.DetailModel
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _detailData = MutableLiveData<DetailModel>()
    val detailData: MutableLiveData<DetailModel> = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: MutableLiveData<Boolean> = _isFavorite

    fun processEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetDetailUser -> {
                getUserDetail(
                    event.username,
                    event.lifecycleOwner
                )
            }

            is DetailEvent.GetFavoriteUser -> {
                getFavoriteUser(
                    event.username,
                    event.lifecycleOwner
                )
            }

            is DetailEvent.AddOrDeleteFavoriteUser -> {
                addOrDeleteFavoriteUser(event.favoriteUser)
            }
        }
    }

    private fun getFavoriteUser(username: String, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            userRepository.getFavoriteUserByName(username).observe(lifecycleOwner) { favoriteUser ->
                _isFavorite.value = favoriteUser != null
            }
        }
    }

    private fun addOrDeleteFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            if (isFavorite.value == true) {
                deleteFavoriteUser(favoriteUser.username)
            } else {
                addFavoriteUser(favoriteUser)
            }
        }
    }

    private fun deleteFavoriteUser(username: String) {
        viewModelScope.launch {
            userRepository.deleteFavorite(username)
        }
        _isFavorite.value = false
    }

    private fun addFavoriteUser(favoriteUser: FavoriteUser) {
        viewModelScope.launch {
            userRepository.insertFavorite(favoriteUser)
        }
        _isFavorite.value = true
    }

    private fun getUserDetail(username: String, lifecycleOwner: LifecycleOwner) {
        _isLoading.value = true
        viewModelScope.launch {
            userRepository.getUserDetail(username).observe(lifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _detailData.value = result.data
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        Log.e(
                            "DetailViewModel",
                            "onFailure: ${result.error}"
                        )
                    }
                }
            }
        }
    }
}