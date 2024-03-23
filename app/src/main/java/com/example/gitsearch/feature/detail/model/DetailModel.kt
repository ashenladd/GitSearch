package com.example.gitsearch.feature.detail.model

data class DetailModel(
    val name : String,
    val username : String,
    val avatarUrl : String,
    val publicRepos : Int,
    val privateRepos : Int,
    val followers : Int,
    val following : Int,
    val createdAt : String
)
