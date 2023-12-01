package com.example.cleansound.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cleansound.adapter.TracksAdapter
import com.example.cleansound.model.FeaturedPlaylist
import com.example.cleansound.model.PlaylistItem
import com.example.cleansound.model.Playlists
import com.example.cleansound.model.Track
import com.example.cleansound.repositories.SpotifyRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class HomeViewModel(private val spotifyRepository: SpotifyRepository) : ViewModel() {

    private val _playlist = MutableLiveData<FeaturedPlaylist>()
    val playlist : LiveData<FeaturedPlaylist> get() = _playlist

    private val _isInitialLoadComplete = MutableLiveData<Boolean>()
    val isInitialLoadComplete : LiveData<Boolean> get() = _isInitialLoadComplete

    private val _tracks = spotifyRepository.getTracksForPlaylist()
        .cachedIn(viewModelScope)
        .asLiveData()
    val tracks : LiveData<PagingData<com.example.cleansound.local.model.Track>> get() = _tracks

    /**
     * Performing the necessaries checking both remote api and tracks locally
     * and set the flag status to be true to navigate to other fragment (login)
     */
    private val tempAdapter = TracksAdapter()
    init {
        viewModelScope.launch {
            spotifyRepository.getTracksForPlaylist().cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    tempAdapter.submitData(pagingData)
                    _isInitialLoadComplete.value = true
                }
        }
    }
    fun fetchPlaylist() {
        viewModelScope.launch {
            val result = spotifyRepository.getFeaturedPlaylists()
            if (result.isSuccess) {
                _playlist.value = result.getOrNull()
            } else {
                val exception = result.exceptionOrNull()
                println("Error fetching playlists: ${exception?.message}")
            }
        }
    }


}