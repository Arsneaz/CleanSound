package com.example.cleansound.model.response.tracks

import com.google.gson.annotations.SerializedName

data class ArtistsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("images")
	val images: List<com.example.cleansound.model.response.tracks.ImagesItem?>? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null
)