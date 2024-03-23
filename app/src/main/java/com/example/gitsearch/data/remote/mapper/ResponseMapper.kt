package com.example.gitsearch.data.remote.mapper

import com.example.gitsearch.data.remote.response.DetailResponse
import com.example.gitsearch.data.remote.response.FollowerResponseItem
import com.example.gitsearch.data.remote.response.FollowingResponseItem
import com.example.gitsearch.data.remote.response.ItemsItem
import com.example.gitsearch.feature.detail.follow.model.FollowModel
import com.example.gitsearch.feature.detail.model.DetailModel
import com.example.gitsearch.feature.search.model.SearchModel

fun ItemsItem.toModel() = SearchModel(
    login = login.orEmpty(),
    id = id ?: 0,
    avatarUrl = avatarUrl.orEmpty()
)

fun DetailResponse.toModel() = DetailModel(
    name = name.orEmpty(),
    username = login.orEmpty(),
    avatarUrl = avatarUrl.orEmpty(),
    publicRepos = publicRepos ?: 0,
    privateRepos = totalPrivateRepos ?: 0,
    followers = followers ?: 0,
    following = following ?: 0,
    createdAt = createdAt.orEmpty()
)

fun FollowerResponseItem.toModel() = FollowModel(
    username = login.orEmpty(),
    avatar = avatarUrl.orEmpty()
)

fun FollowingResponseItem.toModel() = FollowModel(
    username = login.orEmpty(),
    avatar = avatarUrl.orEmpty()
)
