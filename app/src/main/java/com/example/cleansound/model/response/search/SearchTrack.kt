package com.example.cleansound.model.response.search

import com.google.gson.annotations.SerializedName

data class SearchTrack(

	@field:SerializedName("tracks")
	val tracks: com.example.cleansound.model.response.search.Tracks? = null
)