package com.example.gitsearch.feature.detail

sealed class DetailEvent {
    data class GetDetailUser(val username: String) : DetailEvent()
}