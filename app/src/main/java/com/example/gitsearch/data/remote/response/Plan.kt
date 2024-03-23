package com.example.gitsearch.data.remote.response

import com.google.gson.annotations.SerializedName

data class Plan(

	@field:SerializedName("private_repos")
	val privateRepos: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("collaborators")
	val collaborators: Int? = null,

	@field:SerializedName("space")
	val space: Int? = null
)