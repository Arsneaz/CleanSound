package com.example.cleansound.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentRegisterBinding
import com.example.cleansound.repositories.AuthRepository

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
    ): View {
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

        // This still leaves a unit testing when the user has make an account and not setting up the username and photo profile
        binding.btnRegister.setOnClickListener{
            val email = binding.tiRegisterEmail.text.toString()
            val password = binding.tiRegisterPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.register(email, password)
            }
        }

        binding.tvNavigateLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}