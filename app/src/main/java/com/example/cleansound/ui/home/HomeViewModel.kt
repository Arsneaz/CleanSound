package com.example.cleansound.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansound.model.FeaturedPlaylist
import com.example.cleansound.model.Track
import com.example.cleansound.repositories.SpotifyRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val spotifyRepository: SpotifyRepository) : ViewModel() {

    private val _playlist = MutableLiveData<Result<FeaturedPlaylist>>()
    val playlist : LiveData<Result<FeaturedPlaylist>> get() = _playlist

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> = _tracks

    fun fetchPlaylist() {
        viewModelScope.launch {
            val result = spotifyRepository.getFeaturedPlaylists()
            _playlist.value = result
        }
    }

    fun fetchPlaylistTracks() {
        viewModelScope.launch {
            val result = spotifyRepository.getFeaturedPlaylistTracks()
            if (result.isSuccess) {
                _tracks.value = result.getOrNull()
            } else {
                _tracks.value = emptyList()
                println("List is still empty")
            }
        }
    }


}