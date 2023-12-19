package com.example.cleansound.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansound.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> get() = _currentUser

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    fun register(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.register(email, password)
            handleAuthResult(result)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            handleAuthResult(result)
        }
    }

    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
        _isLoggedIn.value = false
    }

    private fun handleAuthResult(result: Result<FirebaseUser>) {
        result.onSuccess { user ->
            _currentUser.value = user
            _isLoggedIn.value = true
        }.onFailure {
            _currentUser.value = null
            _isLoggedIn.value = false
        }
    }
}
