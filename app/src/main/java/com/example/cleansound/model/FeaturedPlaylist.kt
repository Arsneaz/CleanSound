package com.example.cleansound.model

import com.google.gson.annotations.SerializedName

data class FeaturedPlaylist(
	@SerializedName("playlists")
	val playlists: Playlists? = null
)

data class PlaylistItem(

	@SerializedName("id")
	val id: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("tracks")
	val tracks: Tracks? = null
)
data class Playlists(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("items")
	val items: List<PlaylistItem?>? = null
)

data class ImagesItem(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Tracks(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: String? = null
)
