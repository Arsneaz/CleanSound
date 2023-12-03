package com.example.cleansound.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.ui.home.HomeViewModel

class SplashViewModelFactory(private val spotifyRepository: SpotifyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(spotifyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
