package com.example.cleansound.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cleansound.adapter.FeaturedTracksAdapter
import com.example.cleansound.repositories.SpotifyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select

class SplashViewModel(private val spotifyRepository: SpotifyRepository) : ViewModel() {

    private val _isInitialLoadComplete = MutableLiveData<Boolean>()
    val isInitialLoadComplete : LiveData<Boolean> get() = _isInitialLoadComplete

    /**
     * Either fetching the data if the Refresh Timeout is Expired
     * or just wait 3 seconds before navigate to other fragments
     * # Shootout to Sebastian for this KekW idea
     */

    init {
        viewModelScope.launch {
            val fetchDataJob = async {
                spotifyRepository.getTracksForPlaylist().cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        val tempAdapter = FeaturedTracksAdapter { _ -> }
                        tempAdapter.submitData(pagingData)
                    }
            }

            // Wait for either data fetching to complete or a 3 seconds delay
            val result = select {
                fetchDataJob.onAwait { true } // Data fetched successfully
                onTimeout(4000) { false } // Timeout, no need to fetch data
            }
            _isInitialLoadComplete.value = result
        }
    }

}