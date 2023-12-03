package com.example.cleansound.model.playlists

import com.google.gson.annotations.SerializedName

data class FeaturedPlaylists(

	@field:SerializedName("playlists")
	val playlists: Playlists? = null

)