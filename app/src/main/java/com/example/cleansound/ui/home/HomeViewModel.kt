package com.example.cleansound.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cleansound.local.model.Track
import com.example.cleansound.model.playlists.FeaturedPlaylists
import com.example.cleansound.model.playlists.Playlists
import com.example.cleansound.model.search.ItemsItem
import com.example.cleansound.model.track.SingleTrack
import com.example.cleansound.repositories.SpotifyRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val spotifyRepository: SpotifyRepository) : ViewModel() {

    /**
     * Properties Live Data in the SpotifyRepository
     */
    private val _playlist = MutableLiveData<List<com.example.cleansound.model.playlists.ItemsItem?>?>()
    val playlist : LiveData<List<com.example.cleansound.model.playlists.ItemsItem?>?> get() = _playlist

    private val _searchResult = MutableLiveData<List<ItemsItem?>>()
    val searchResult : LiveData<List<ItemsItem?>> get() = _searchResult

    private val _tracks = spotifyRepository.getTracksForPlaylist()
        .cachedIn(viewModelScope)
        .asLiveData()
    val tracks : LiveData<PagingData<Track>> get() = _tracks

    private val _singleTrack = MutableLiveData<SingleTrack?>()
    val singleTrack : LiveData<SingleTrack?> get() = _singleTrack

    private val _featuredTracks = MutableLiveData<List<com.example.cleansound.model.tracks.Track?>?>()
    val featuredTracks : LiveData<List<com.example.cleansound.model.tracks.Track?>?> get() = _featuredTracks
    fun getFeaturedTracks(playlistId: String?) {
        viewModelScope.launch {
            val results = spotifyRepository.getFeaturedTracks(playlistId)
            if (results.isSuccess) {
                val tracks = results.getOrNull()?.items?.mapNotNull { it?.track } ?: emptyList()
                println(tracks)
                _featuredTracks.value = tracks
            } else {
                val exception = results.exceptionOrNull()
                println("Error fetching searching tracks: ${exception?.message}")
            }
        }
    }
    fun searchTracks(query: String) {
        viewModelScope.launch {
            val results = spotifyRepository.searchTracks(query)
            if (results.isSuccess) {
                _searchResult.value = results.getOrNull()
            } else {
                val exception = results.exceptionOrNull()
                println("Error fetching searching tracks: ${exception?.message}")
            }
        }
    }

    fun getSingleTrack(trackId : String?) {
        viewModelScope.launch {
            val result = spotifyRepository.getTrack(trackId)
            if (result.isSuccess) {
                _singleTrack.value = result.getOrNull()
            } else {
                val exception = result.exceptionOrNull()
                println("Error fetching single track: ${exception?.message}")
            }
        }
    }

    fun fetchPlaylist() {
        viewModelScope.launch {
            val result = spotifyRepository.getFeaturedPlaylists()
            if (result.isSuccess) {
                _playlist.value = result.getOrNull()?.items
            } else {
                val exception = result.exceptionOrNull()
                println("Error fetching playlists: ${exception?.message}")
            }
        }
    }


}