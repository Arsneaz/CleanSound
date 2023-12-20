package com.example.cleansound.model.response.search

import com.google.gson.annotations.SerializedName

data class Album(

	@field:SerializedName("images")
	val images: List<com.example.cleansound.model.response.search.ImagesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("artists")
	val artists: List<com.example.cleansound.model.response.search.ArtistsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("album_type")
	val albumType: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)