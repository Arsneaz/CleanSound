package com.example.cleansound.model.playlists

import com.google.gson.annotations.SerializedName

data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)