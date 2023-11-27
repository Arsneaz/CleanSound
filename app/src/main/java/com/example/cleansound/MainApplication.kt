package com.example.cleansound

import android.app.Application
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.spotify.SpotifyAuthenticator
import com.example.cleansound.spotify.SpotifyConfig
import com.example.cleansound.spotify.SpotifyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainApplication : Application() {

    lateinit var spotifyClient : SpotifyConfig
    lateinit var spotifyRepository: SpotifyRepository
    lateinit var spotifyService: SpotifyService

    override fun onCreate() {
        super.onCreate()
        val spotifyAuthenticator = SpotifyAuthenticator()

        GlobalScope.launch {
            spotifyAuthenticator.initializeApi()
            val tokenAccess = spotifyAuthenticator.getTokenAccess()
            spotifyClient = SpotifyConfig(applicationContext, tokenAccess)
            spotifyService = spotifyClient.createService()
            spotifyRepository = SpotifyRepository(spotifyService)
        }
    }
}