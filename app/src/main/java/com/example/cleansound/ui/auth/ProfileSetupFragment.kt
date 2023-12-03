package com.example.cleansound.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentProfileSetupBinding
import com.example.cleansound.repositories.AuthRepository

class ProfileSetupFragment : Fragment() {

    private val profileSetupViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSetupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileSetupViewModel.insertUserStatus.observe(viewLifecycleOwner) {insertStatus ->
            if (insertStatus) {
                findNavController().navigate(R.id.action_profileSetupFragment_to_navigation_home)
            }
        }

        binding.proceedBtn.setOnClickListener{
            val userName = binding.userNameProfile.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(requireContext(),"Please fill the username.",Toast.LENGTH_LONG).show()
            } else {
                profileSetupViewModel.addNewUser(userName)
            }
        }
    }
}