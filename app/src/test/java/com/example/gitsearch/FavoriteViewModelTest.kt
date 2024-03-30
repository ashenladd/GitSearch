package com.example.gitsearch

import androidx.lifecycle.liveData
import com.example.gitsearch.data.local.entity.FavoriteUser
import com.example.gitsearch.data.repository.UserRepository
import com.example.gitsearch.feature.favorite.FavoriteViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @Mock
    lateinit var userRepository: UserRepository

    private val viewModel: FavoriteViewModel by lazy {
        FavoriteViewModel(userRepository)
    }

    @Test
    fun `test getFavorite returns all favorite users`() {
        val favoriteUsers = liveData<List<FavoriteUser>> {
            listOf(
                FavoriteUser(
                    username = "b",
                    avatarUrl = "https://avatars.githubusercontent.com/u/31712?v=4"
                ),
                FavoriteUser(
                    username = "balupton",
                    avatarUrl = "https://avatars.githubusercontent.com/u/61148?v=4"
                ),
            )
        }
        `when`(userRepository.getAllFavoriteUsers()).thenReturn(favoriteUsers)

        val result = viewModel.getFavorite()

        assertEquals(
            favoriteUsers,
            result
        )
    }
}