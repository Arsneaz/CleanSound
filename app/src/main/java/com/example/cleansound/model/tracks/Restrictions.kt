package com.example.cleansound.model.tracks

import com.google.gson.annotations.SerializedName

data class Restrictions(

	@field:SerializedName("reason")
	val reason: String? = null
)