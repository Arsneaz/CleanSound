package com.example.cleansound.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.repositories.LocalRepository

class ProfileSetupViewModelFactory(private val localRepository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileSetupViewModel::class.java)) {
            return ProfileSetupViewModel(localRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
