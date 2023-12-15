package com.example.cleansound.ui.MotionLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cleansound.R
import com.example.cleansound.databinding.FragmentMotionLayout2Binding


class MotionLayout : Fragment() {

    private var _binding: FragmentMotionLayout2Binding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMotionLayout2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnStart.setOnClickListener{
            findNavController().navigate(R.id.action_motionLayout2_to_loginFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}