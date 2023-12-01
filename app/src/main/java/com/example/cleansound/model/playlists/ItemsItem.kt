package com.example.cleansound.model.playlists

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("snapshot_id")
	val snapshotId: String? = null,

	@field:SerializedName("collaborative")
	val collaborative: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("primary_color")
	val primaryColor: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("tracks")
	val tracks: Tracks? = null,

	@field:SerializedName("public")
	val jsonMemberPublic: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null
)