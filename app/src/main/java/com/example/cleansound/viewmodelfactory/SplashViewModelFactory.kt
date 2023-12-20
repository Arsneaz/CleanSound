package com.example.cleansound.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.viewmodel.SplashViewModel

class SplashViewModelFactory(private val spotifyRepository: SpotifyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(spotifyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
