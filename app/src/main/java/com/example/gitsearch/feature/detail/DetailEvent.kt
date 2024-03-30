package com.example.gitsearch.feature.detail

import androidx.lifecycle.LifecycleOwner
import com.example.gitsearch.data.local.entity.FavoriteUser

sealed class DetailEvent {
    data class GetDetailUser(val username: String, val lifecycleOwner: LifecycleOwner) : DetailEvent()
    data class GetFavoriteUser(val username: String, val lifecycleOwner: LifecycleOwner) : DetailEvent()
    data class AddOrDeleteFavoriteUser(val favoriteUser: FavoriteUser) : DetailEvent()
}