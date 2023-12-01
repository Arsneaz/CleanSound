package com.example.cleansound

import android.app.Application
import com.example.cleansound.local.data.AppDatabase
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.spotify.SpotifyAuthenticator
import com.example.cleansound.spotify.SpotifyConfig
import com.example.cleansound.spotify.SpotifyService
import kotlinx.coroutines.runBlocking

class MainApplication : Application() {

    val spotifyRepository : SpotifyRepository by lazy {
        initializeSpotifyService()
    }

    private fun initializeSpotifyService() : SpotifyRepository {
        val spotifyAuthenticator = SpotifyAuthenticator()
        runBlocking { spotifyAuthenticator.initializeApi() }
        val tokenAccess = runBlocking { spotifyAuthenticator.getTokenAccess() }
        val spotifyClient = SpotifyConfig(applicationContext, tokenAccess)
        val spotifyService = spotifyClient.createService()
        val appDatabase = AppDatabase.getDatabase(applicationContext)

        return SpotifyRepository(spotifyService, appDatabase)

    }

}