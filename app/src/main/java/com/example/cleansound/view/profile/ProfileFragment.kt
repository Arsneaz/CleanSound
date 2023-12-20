package com.example.cleansound.view.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentProfileBinding
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.viewmodel.AuthViewModel
import com.example.cleansound.viewmodelfactory.AuthViewModelFactory
import com.example.cleansound.viewmodel.ProfileSetupViewModel
import com.example.cleansound.viewmodelfactory.ProfileSetupViewModelFactory

class ProfileFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private val profileViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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

        binding.btnLogout.setOnClickListener {
            authViewModel.signOut()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.splash, true)
                .build()

            findNavController().navigate(R.id.loginFragment, null, navOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}