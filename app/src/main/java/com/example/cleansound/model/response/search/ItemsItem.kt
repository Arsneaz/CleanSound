package com.example.cleansound.model.response.search

import com.google.gson.annotations.SerializedName

data class ItemsItem(

    @field:SerializedName("album")
	val album: com.example.cleansound.model.response.search.Album? = null,

    @field:SerializedName("artists")
	val artists: List<com.example.cleansound.model.response.search.ArtistsItem?>? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("href")
	val href: String? = null,

    @field:SerializedName("id")
	val id: String? = null
)