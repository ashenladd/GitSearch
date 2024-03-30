package com.example.gitsearch.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitsearch.data.repository.UserRepository
import com.example.gitsearch.di.Injection
import com.example.gitsearch.feature.detail.DetailViewModel
import com.example.gitsearch.feature.favorite.FavoriteViewModel
import com.example.gitsearch.feature.search.SearchViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val pref: SettingPreferences,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                userRepository,
                pref
            ) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideFavoriteRepository(context),
                    Injection.provideSettingPreferences(context)
                )
            }.also { instance = it }
    }
}