package com.example.cleansound.model.track

import com.google.gson.annotations.SerializedName

data class Followers(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: String? = null
)