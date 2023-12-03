package com.example.cleansound.ui.auth

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleansound.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> get() = _currentUser

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    fun register(email: String, password: String) {
        authRepository.register(email, password, ::onAuthResult)
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password, ::onAuthResult)
    }

    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    private fun onAuthResult(user: FirebaseUser?, error: String?) {
        _currentUser.value = user
        _isLoggedIn.value = user != null
    }


}
