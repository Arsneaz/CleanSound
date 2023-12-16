package com.example.cleansound.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.utils.MainDispatcherRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AuthViewModel
    private val mockAuthRepository = mock(AuthRepository::class.java)
    private val mockFirebaseAuth = mock(FirebaseAuth::class.java)

    @Before
    fun setup() {
        viewModel = AuthViewModel(mockAuthRepository, mockFirebaseAuth)
    }

    @Test
    fun `register with valid credentials updates currentUser and loginStatus`() = runTest {
        val mockFirebaseUser = mock(FirebaseUser::class.java)

        `when`(mockAuthRepository.register(anyString(), anyString()))
            .thenReturn(Result.success(mockFirebaseUser))

        var currentUserResult: FirebaseUser? = null
        viewModel.currentUser.observeForever { currentUserResult = it }

        var isLoggedInResult: Boolean? = null
        viewModel.isLoggedIn.observeForever { isLoggedInResult = it }

        viewModel.register("user@example.com", "password")

        assertEquals(mockFirebaseUser, currentUserResult)
        assertTrue(isLoggedInResult ?: false)

        viewModel.currentUser.removeObserver { currentUserResult = it }
        viewModel.isLoggedIn.removeObserver { isLoggedInResult = it }
    }


    @Test
    fun `login with valid credentials and update the live data of currentUser and loginStatus`() = runTest {
        val mockFirebaseUser = mock(FirebaseUser::class.java)

        `when`(mockAuthRepository.login(anyString(), anyString()))
            .thenReturn(Result.success(mockFirebaseUser))

        var currentUserResult: FirebaseUser? = null
        viewModel.currentUser.observeForever { currentUserResult = it }

        var isLoggedInResult: Boolean? = null
        viewModel.isLoggedIn.observeForever { isLoggedInResult = it }

        viewModel.login("xenoare@gmail.com", "12345678")

        assertEquals(mockFirebaseUser, currentUserResult)
        assertTrue(isLoggedInResult ?: false)

        viewModel.currentUser.removeObserver { currentUserResult = it }
        viewModel.isLoggedIn.removeObserver { isLoggedInResult = it }
    }
}