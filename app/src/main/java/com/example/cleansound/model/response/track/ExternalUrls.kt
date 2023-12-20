package com.example.cleansound.model.response.track

import com.google.gson.annotations.SerializedName

data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)