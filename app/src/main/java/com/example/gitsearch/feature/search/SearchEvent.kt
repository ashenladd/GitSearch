package com.example.gitsearch.feature.search

sealed class SearchEvent {
    data class SearchUser(val query: String) : SearchEvent()
}