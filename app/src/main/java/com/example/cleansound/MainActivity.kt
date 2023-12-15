package com.example.cleansound

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adamratzman.spotify.models.Token
import com.example.cleansound.databinding.ActivityMainBinding
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.spotify.SpotifyAuthenticator
import com.example.cleansound.spotify.SpotifyConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.splash,
                R.id.motionLayout2,
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.profileSetupFragment -> {
                    navView.visibility = View.GONE
                }
                else -> navView.visibility = View.VISIBLE
            }

            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when(navController.currentDestination?.id) {
                        R.id.navigation_home, R.id.loginFragment -> {
                            showExitConfirmationDialog()
                        }
                        R.id.navigation_favorite,
                        R.id.navigation_notifications,
                        R.id.navigation_dashboard ->  {
                            // This code is also kind not work the way I like
                            navController.popBackStack(R.id.navigation_home, false)
                        }
                        else -> navController.navigateUp()
                    }
                }
            }

            onBackPressedDispatcher.addCallback(this, callback)
        }

        navView.setupWithNavController(navController)
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Do you want to exit the app?")
            .setPositiveButton("Exit") { dialog, _ ->
                dialog.dismiss()
                finish() // Close the app
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
