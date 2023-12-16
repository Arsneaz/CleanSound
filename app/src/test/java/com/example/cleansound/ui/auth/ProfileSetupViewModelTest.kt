package com.example.cleansound.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cleansound.local.model.User
import com.example.cleansound.repositories.LocalRepository
import com.example.cleansound.utils.MainDispatcherRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ProfileSetupViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProfileSetupViewModel
    private val mockLocalRepository = mock(LocalRepository::class.java)
    private val mockFirebaseAuth = mock(FirebaseAuth::class.java)


    @Before
    fun setup() {
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.email).thenReturn("test@example.com")
        `when`(mockFirebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        viewModel = ProfileSetupViewModel(mockLocalRepository, mockFirebaseAuth)
    }

    @Test
    fun `addNewUser and updates the insertUserStatus to true on success`() = runTest {
        val testUser = User("test@email.com", "Test User", "Description", "PathImage")

        var insertStatus = false
        viewModel.insertUserStatus.observeForever { status ->
            insertStatus = status
        }

        viewModel.addNewUser(testUser.emailId, testUser.name!!, testUser.desc!!, testUser.imagePath!!)

        assertTrue(insertStatus)

        viewModel.insertUserStatus.removeObserver { status ->
            insertStatus = status
        }


    }

    @Test
    fun getUserProfile() {
    }

    @Test
    fun addNewUser() {
    }
}