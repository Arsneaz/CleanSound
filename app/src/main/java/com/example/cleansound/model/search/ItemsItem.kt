package com.example.cleansound.model.search

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("album")
	val album: Album? = null,

	@field:SerializedName("artists")
	val artists: List<ArtistsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)