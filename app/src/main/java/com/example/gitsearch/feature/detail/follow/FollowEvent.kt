package com.example.gitsearch.feature.detail.follow

sealed class FollowEvent {
    data class GetFollowers(val username: String) : FollowEvent()
    data class GetFollowing(val username: String) : FollowEvent()
}