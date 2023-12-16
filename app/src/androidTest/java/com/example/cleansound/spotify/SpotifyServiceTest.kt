package com.example.cleansound.spotify

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cleansound.ui.home.HomeFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpotifyServiceTest {

    private lateinit var mockWebServer : MockWebServer
    private lateinit var spotifyService: SpotifyService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        spotifyService = retrofit.create(SpotifyService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get track response from the spotify service`() {
        val trackId = "2up3OPMp9Tb4dAKM2erWXQ"
        val mockResponseJson = readTestResourceFile("track_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponseJson))

        val response = runBlocking { spotifyService.getTrack(trackId) }

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun `get featured playlist from the service`() {
        val mockResponseJson = readTestResourceFile("playlist_featured_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponseJson))
        val scenario = launchFragmentInContainer<HomeFragment>()


        val response = runBlocking { spotifyService.getFeaturedPlaylist() }

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun `get playlist tracks from the service`() {
        val playlistId = "3cEYpjA9oz9GiPac4AsH4n"
        val mockResponseJson = readTestResourceFile("playlist_track_items_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponseJson))

        val response = runBlocking { spotifyService.getPlaylistTracks(playlistId) }

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun `get search track from the service`() {
        val query = "Soledad"
        val mockResponseJson = readTestResourceFile("search_track_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponseJson))

        val response = runBlocking { spotifyService.searchTracks(query) }

        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun `get track response with 404 error`() {
        val trackId = "nonExistentTrackId"
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val response = runBlocking { spotifyService.getTrack(trackId) }

        assertFalse(response.isSuccessful)
        assertEquals(response.code(), 404)
    }

    @Test
    fun `get track response with server error`() {
        val trackId = "123"
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val response = runBlocking { spotifyService.getTrack(trackId) }

        assertFalse(response.isSuccessful)
        assertEquals(response.code(), 500)
    }

    @Test
    fun `get track response with malformed JSON`() {
        val trackId = "123"
        val malformedJson = "This is not a valid JSON"
        mockWebServer.enqueue(MockResponse().setResponseCode(400). setBody(malformedJson))

        val response = runBlocking { spotifyService.getTrack(trackId) }

        assertFalse(response.isSuccessful)
    }


    private fun readTestResourceFile(fileName: String): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

}