package com.example.gitsearch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
)
