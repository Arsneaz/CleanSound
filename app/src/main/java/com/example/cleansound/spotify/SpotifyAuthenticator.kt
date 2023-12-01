package com.example.cleansound.spotify

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi
import com.example.cleansound.BuildConfig

class SpotifyAuthenticator {

     lateinit var spotifyApi : SpotifyAppApi

    suspend fun initializeApi() {
         spotifyApi = spotifyAppApi(BuildConfig.SPOTIFY_CLIENT_ID, BuildConfig.SPOTIFY_CLIENT_SECRET).build()
    }

     fun getTokenAccess(): String {
        return spotifyApi.token.accessToken ?: ""
    }
}