package com.example.gitsearch.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.gitsearch.data.Result
import com.example.gitsearch.data.local.dao.FavoriteUserDao
import com.example.gitsearch.data.local.entity.FavoriteUser
import com.example.gitsearch.data.remote.mapper.toModel
import com.example.gitsearch.data.remote.network.ApiService
import com.example.gitsearch.feature.detail.model.DetailModel
import com.example.gitsearch.feature.search.model.SearchModel

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,
) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(
                    apiService,
                    favoriteUserDao,
                ).also { instance = it }
            }
    }

    fun searchUsers(query: String): LiveData<Result<List<SearchModel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchUser(query)
            val items = response.items
            val data = items?.map {
                it?.toModel() ?: SearchModel(
                    login = "",
                    id = 0,
                    avatarUrl = ""
                )
            } ?: emptyList()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserDetail(username: String): LiveData<Result<DetailModel>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username)
            val data = response.toModel()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun insertFavorite(favoriteUser: FavoriteUser) {
        favoriteUserDao.insert(favoriteUser)
    }

    suspend fun updateFavorite(favoriteUser: FavoriteUser) {
        favoriteUserDao.update(favoriteUser)
    }

    suspend fun deleteFavorite(username: String) {
        favoriteUserDao.delete(username)
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getAllFavoriteUsers()
    }

    fun getFavoriteUserByName(username: String): LiveData<FavoriteUser> =
        favoriteUserDao.getFavoriteUserByUsername(username)

}
