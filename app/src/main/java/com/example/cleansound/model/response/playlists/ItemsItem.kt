package com.example.cleansound.model.response.playlists

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("images")
	val images: List<com.example.cleansound.model.response.playlists.ImagesItem?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("tracks")
	val tracks: com.example.cleansound.model.response.playlists.Tracks? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)