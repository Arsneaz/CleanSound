package com.example.cleansound.repositories

import com.example.cleansound.model.local.data.AppDatabase
import com.example.cleansound.spotify.SpotifyService
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock

class SpotifyRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var spotifyRepository: SpotifyRepository
    private lateinit var mockSpotifyService : SpotifyService
    private lateinit var mockAppDatabase: AppDatabase

    @Before
    fun setup() {
        mockSpotifyService = mock(SpotifyService::class.java)
        mockAppDatabase = mock(AppDatabase::class.java)
        spotifyRepository = SpotifyRepository(mockSpotifyService, mockAppDatabase)

        mockWebServer = MockWebServer().apply { start() }

    }

    @Test
    fun `get track details from the service`() {
        val trackId = "11dFghVXANMlKmJXsNCbNl"
    }

    @Test
    fun searchTracks() {
    }

    @Test
    fun getTrack() {
    }

    @Test
    fun getFeaturedTracks() {
    }

    @Test
    fun getTracksForPlaylist() {
    }
}