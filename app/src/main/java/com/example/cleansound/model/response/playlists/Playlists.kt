package com.example.cleansound.model.response.playlists

import com.google.gson.annotations.SerializedName

data class Playlists(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("items")
	val items: List<com.example.cleansound.model.response.playlists.ItemsItem?>? = null
)