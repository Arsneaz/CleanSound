package com.example.cleansound.model.response.playlists

import com.google.gson.annotations.SerializedName

data class FeaturedPlaylists(

	@field:SerializedName("playlists")
	val playlists: com.example.cleansound.model.response.playlists.Playlists? = null

)