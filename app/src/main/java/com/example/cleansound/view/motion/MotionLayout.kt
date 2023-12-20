package com.example.cleansound.view.motion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentMotionLayoutBinding

class MotionLayout : Fragment() {

    private var _binding: FragmentMotionLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMotionLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener{
            findNavController().navigate(R.id.action_motionLayout_to_loginFragment)
            val sharedPref = activity?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            if (sharedPref != null) {
                with(sharedPref.edit()) {
                    putBoolean("ONBOARDING_COMPLETE", true)
                    apply()
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}