package com.example.cleansound.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository(private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    suspend fun register(email: String, password: String): Result<FirebaseUser> = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.success(firebaseAuth.currentUser!!))
                } else {
                    continuation.resume(Result.failure(task.exception ?: Exception("Registration failed")))
                }
            }
    }

    suspend fun login(email: String, password: String): Result<FirebaseUser> = suspendCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.success(firebaseAuth.currentUser!!))
                } else {
                    continuation.resume(Result.failure(task.exception ?: Exception("Login failed")))
                }
            }
    }


    fun signOut(){
        firebaseAuth.signOut()
    }
}

