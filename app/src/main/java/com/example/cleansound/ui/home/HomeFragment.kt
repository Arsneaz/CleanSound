package com.example.cleansound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentHomeBinding
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.ui.auth.AuthViewModel
import com.example.cleansound.ui.auth.AuthViewModelFactory

class HomeFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root

        binding.signOutBtn.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_loginFragment)
            viewModel.signOut()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}