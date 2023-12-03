package com.example.cleansound.model.search

import com.adamratzman.spotify.utils.ExternalUrls
import com.google.gson.annotations.SerializedName

data class ArtistsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)