package com.example.cleansound.model.tracks

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("added_at")
	val addedAt: String? = null,

	@field:SerializedName("added_by")
	val addedBy: AddedBy? = null,

	@field:SerializedName("is_local")
	val isLocal: Boolean? = null,

	@field:SerializedName("track")
	val track: Track? = null
)