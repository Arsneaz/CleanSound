package com.example.cleansound.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentLoginBinding
import com.example.cleansound.repositories.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater)
        val root = binding.root

        viewModel.currentUser.observe(viewLifecycleOwner) {user ->
            if (user != null) {
                findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
            } else {
                Toast.makeText(requireContext(),"Something wrong with the server when Login",Toast.LENGTH_LONG).show()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Redirecting to the register page
        binding.signInBtn.setOnClickListener {
            val email = binding.emailEditSignUp.text.toString()
            val password = binding.passEditSignUp.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            }
            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()

        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}