package com.example.cleansound.model.response.track

import com.google.gson.annotations.SerializedName

data class ArtistsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: com.example.cleansound.model.response.track.ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("images")
	val images: List<com.example.cleansound.model.response.track.ImagesItem?>? = null,

	@field:SerializedName("followers")
	val followers: com.example.cleansound.model.response.track.Followers? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("popularity")
	val popularity: Int? = null
)