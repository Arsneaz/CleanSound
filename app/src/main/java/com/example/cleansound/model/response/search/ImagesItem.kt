package com.example.cleansound.model.response.search

import com.google.gson.annotations.SerializedName

data class ImagesItem(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)