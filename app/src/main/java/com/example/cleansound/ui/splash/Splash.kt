package com.example.cleansound.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.databinding.SplashBinding
import com.example.cleansound.ui.home.HomeViewModel
import com.example.cleansound.ui.home.SpotifyViewModelFactory

class Splash: Fragment() {

    private val spotifyViewModel: SplashViewModel by viewModels {
        val spotifyRepository = (requireActivity().application as MainApplication).spotifyRepository
        SplashViewModelFactory(spotifyRepository)
    }

    private var _binding: SplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayLoadingIndicator(true)

        spotifyViewModel.isInitialLoadComplete.observe(viewLifecycleOwner) {isComplete ->
            if (isComplete) {
                displayLoadingIndicator(false)
                findNavController().navigate(R.id.action_splash_to_motionLayout2)
            } else {
                displayLoadingIndicator(false)
                findNavController().navigate(R.id.action_splash_to_motionLayout2)
            }
        }

    }

    private fun displayLoadingIndicator(display: Boolean) {
        binding.loadingProgressBar.visibility = if (display) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}