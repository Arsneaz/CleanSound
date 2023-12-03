package com.example.cleansound.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cleansound.local.model.Track
import com.example.cleansound.local.model.User
import com.example.cleansound.repositories.LocalRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileSetupViewModel(private val localRepository: LocalRepository) : ViewModel() {

    private val userEmail = FirebaseAuth.getInstance().currentUser?.email!!

    private val _insertUserStatus = MutableLiveData<Boolean>()
    val insertUserStatus : LiveData<Boolean> get() = _insertUserStatus

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _favoriteTracks = localRepository.getFavoriteTracks(userEmail)
    val favoriteTracks : LiveData<List<Track>> = _favoriteTracks

    fun addNewUser(userName: String) {
        val newUser = User(userEmail, userName)
        viewModelScope.launch {
            try {
                localRepository.insertUser(newUser)
                _insertUserStatus.value = true
            } catch (e: Exception) {
                _insertUserStatus.value = false
            }
        }

    }

    fun checkIfFavorite(track: Track) {
        viewModelScope.launch {
            val isFavorite = localRepository.isFavoriteTrack(userEmail, track.trackId)
            println("This status is ${isFavorite}")
            _isFavorite.postValue(isFavorite)
        }
    }

    fun toggleFavoriteStatus(track: Track) {
        viewModelScope.launch {
            val currentStatus = _isFavorite.value ?: false
            if (currentStatus) {
                println("Removing from the db")
                localRepository.removeFavoriteTrack(userEmail, track)
            } else {
                println("Inserting into the db")
                localRepository.insertFavoriteTrack(userEmail, track)
            }
            _isFavorite.postValue(!currentStatus)
        }
    }

    fun getFavoriteTracks() {
        viewModelScope.launch {

        }
    }
}