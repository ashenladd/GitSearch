package com.example.gitsearch.feature.search

import androidx.lifecycle.LifecycleOwner

sealed class SearchEvent {
    data class SearchUser(val query: String, val lifecycleOwner: LifecycleOwner) : SearchEvent()
    data class ToggleMode(val isDarkModeActive : Boolean) : SearchEvent()
}