package com.example.cleansound.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentFavoriteBinding
import com.example.cleansound.databinding.FragmentHomeBinding
import com.example.cleansound.databinding.SplashBinding
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.ui.auth.AuthViewModel
import com.example.cleansound.ui.auth.AuthViewModelFactory
import com.example.cleansound.ui.auth.ProfileSetupViewModel
import com.example.cleansound.ui.auth.ProfileSetupViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class FavoriteFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private val profileViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.userProfile.observe(viewLifecycleOwner) {userProfile ->
            binding.tvEmailUser.text = userProfile.emailId
            binding.tvUsername.text = userProfile.name
            binding.tvDescription.text = userProfile.desc
            Glide.with(requireContext())
                .load(Uri.parse(userProfile.imagePath))
                .into(binding.ivProfilePicture)
        }

        binding.SignOutBtn.setOnClickListener {
            authViewModel.signOut()
            findNavController().navigate(R.id.action_navigation_favorite_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}