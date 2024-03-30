package com.example.gitsearch.feature.search

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.Result
import com.example.gitsearch.data.repository.UserRepository
import com.example.gitsearch.feature.search.model.SearchModel
import com.example.gitsearch.util.SettingPreferences
import kotlinx.coroutines.launch

class SearchViewModel(
    private val userRepository: UserRepository,
    private val pref: SettingPreferences,
) :
    ViewModel() {
    private val _listSearch = MutableLiveData<List<SearchModel>>()
    val listSearch: LiveData<List<SearchModel>> = _listSearch

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>(true)
    val isEmpty: LiveData<Boolean> = _isEmpty


    fun processEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchUser -> {
                searchUser(
                    event.query,
                    event.lifecycleOwner
                )
            }

            is SearchEvent.ToggleMode -> {
                saveThemeSetting(event.isDarkModeActive)
            }
        }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    private fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    private fun searchUser(query: String, lifecycleOwner: LifecycleOwner) {
        _isLoading.value = true
        _isEmpty.value = false
        viewModelScope.launch {
            userRepository.searchUsers(query).observe(lifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _listSearch.value = result.data
                        _isEmpty.value = _listSearch.value?.isEmpty()
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        Log.e(
                            "SearchViewModel",
                            "onFailure: ${result.error}"
                        )
                    }
                }
            }
        }
    }
}