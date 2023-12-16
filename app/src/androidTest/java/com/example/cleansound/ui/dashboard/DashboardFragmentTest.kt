package com.example.cleansound.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import com.example.cleansound.R
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cleansound.local.data.AppDatabase
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.spotify.SpotifyService
import com.example.cleansound.ui.home.HomeFragment
import com.example.cleansound.ui.splash.Splash
import com.example.cleansound.utils.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Matcher

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var spotifyRepository: SpotifyRepository
    private lateinit var mockSpotifyService: SpotifyService
    private lateinit var mockAppDatabase: AppDatabase

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mockSpotifyService = retrofit.create(SpotifyService::class.java)

        mockAppDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()

        spotifyRepository = SpotifyRepository(mockSpotifyService, mockAppDatabase)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResourceInstance)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResourceInstance)
    }

    @Test
    fun getTrackFromSearch_keywordInput_success(){

        Log.d("TestDebug", "Launching fragment")
        launchFragmentInContainer<DashboardFragment>()

        Log.d("TestDebug", "Enqueueing mock response")
        val mockResponseJson = readTestResourceFile("search_track_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockResponseJson))

        Log.d("TestDebug", "Performing search action")
        onView(withId(R.id.search_action)).perform(typeText("Soledad"), pressImeActionButton())

        Log.d("TestDebug", "Checking RecyclerView visibility")
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    private fun readTestResourceFile(fileName: String): String {
        val inputStream = InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}