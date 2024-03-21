package com.example.gitsearch.data.response

import com.google.gson.annotations.SerializedName

data class FollowingResponse(

	@field:SerializedName("FollowingResponse")
	val followingResponse: List<FollowingResponseItem?>? = null
)