package com.example.cleansound.model.response.playlists

import com.google.gson.annotations.SerializedName

data class ImagesItem(

	@field:SerializedName("width")
	val width: Any? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Any? = null
)