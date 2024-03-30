package com.example.gitsearch.feature.favorite

import androidx.lifecycle.ViewModel
import com.example.gitsearch.data.repository.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavorite() = userRepository.getAllFavoriteUsers()
}