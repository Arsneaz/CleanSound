package com.example.cleansound.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentRegisterBinding
import com.example.cleansound.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner) {user ->
            if (user != null) {
                findNavController().navigate(R.id.action_registerFragment_to_profileSetupFragment)
            } else {
                Toast.makeText(requireContext(),"User with same email already exists, please change another email",Toast.LENGTH_LONG).show()
            }
        }

        binding.signUpBtn.setOnClickListener{
            val email = binding.tiEmailEditSignUp.text.toString()
            val password = binding.tiPassEditSignUp.text.toString()

            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.register(email, password)
            }
        }

        binding.signInText.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}