package com.example.cleansound.model.tracks

import com.google.gson.annotations.SerializedName

data class ExternalIds(

	@field:SerializedName("ean")
	val ean: String? = null,

	@field:SerializedName("upc")
	val upc: String? = null,

	@field:SerializedName("isrc")
	val isrc: String? = null
)