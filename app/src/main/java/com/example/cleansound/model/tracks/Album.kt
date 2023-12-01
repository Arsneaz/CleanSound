package com.example.cleansound.model.tracks

import com.google.gson.annotations.SerializedName

data class Album(

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("available_markets")
	val availableMarkets: List<String?>? = null,

	@field:SerializedName("restrictions")
	val restrictions: Restrictions? = null,

	@field:SerializedName("release_date_precision")
	val releaseDatePrecision: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("total_tracks")
	val totalTracks: Int? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("artists")
	val artists: List<ArtistsItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("album_type")
	val albumType: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null
)