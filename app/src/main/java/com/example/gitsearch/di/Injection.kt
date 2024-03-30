package com.example.gitsearch.di

import android.content.Context
import com.example.gitsearch.data.local.database.FavoriteRoomDatabase
import com.example.gitsearch.data.remote.network.ApiConfig
import com.example.gitsearch.data.repository.UserRepository
import com.example.gitsearch.util.SettingPreferences
import com.example.gitsearch.util.dataStore

object Injection {
    fun provideFavoriteRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return UserRepository.getInstance(
            apiService,
            dao,
        )
    }

    fun provideSettingPreferences(context: Context) = SettingPreferences.getInstance(context.dataStore)
}