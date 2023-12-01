package com.example.cleansound.model.search

import com.google.gson.annotations.SerializedName

data class SearchTrack(

	@field:SerializedName("tracks")
	val tracks: Tracks? = null
)