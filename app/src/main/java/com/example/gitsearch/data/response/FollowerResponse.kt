package com.example.gitsearch.data.response

import com.google.gson.annotations.SerializedName

data class FollowerResponse(

	@field:SerializedName("FollowerResponse")
	val followerResponse: List<FollowerResponseItem?>? = null
)