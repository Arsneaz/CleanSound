package com.example.cleansound.model.response.tracks

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("track")
	val track: com.example.cleansound.model.response.tracks.Track? = null
)