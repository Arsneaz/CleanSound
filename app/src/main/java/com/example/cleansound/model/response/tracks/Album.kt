package com.example.cleansound.model.response.tracks

import com.google.gson.annotations.SerializedName

data class Album(

    @field:SerializedName("images")
	val images: List<com.example.cleansound.model.response.tracks.ImagesItem?>? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("total_tracks")
	val totalTracks: Int? = null,

    @field:SerializedName("release_date")
	val releaseDate: String? = null,

    @field:SerializedName("artists")
	val artists: List<com.example.cleansound.model.response.tracks.ArtistsItem?>? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("album_type")
	val albumType: String? = null,

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null
)