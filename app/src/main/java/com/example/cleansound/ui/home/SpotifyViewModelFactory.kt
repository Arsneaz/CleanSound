package com.example.cleansound.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.ui.home.HomeViewModel

class SpotifyViewModelFactory(private val spotifyRepository: SpotifyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(spotifyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
