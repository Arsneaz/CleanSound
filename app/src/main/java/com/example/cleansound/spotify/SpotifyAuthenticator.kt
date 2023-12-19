package com.example.cleansound.spotify

import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.spotifyAppApi

class SpotifyAuthenticator {

    lateinit var spotifyApi : SpotifyAppApi

    suspend fun initializeApi() {
         spotifyApi = spotifyAppApi(com.example.cleansound.BuildConfig.SPOTIFY_CLIENT_ID, com.example.cleansound.BuildConfig.SPOTIFY_CLIENT_SECRET).build()
    }

     fun getTokenAccess(): String {
        return spotifyApi.token.accessToken
    }
}