package com.example.cleansound.model.tracks

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("track")
	val track: Track? = null
)