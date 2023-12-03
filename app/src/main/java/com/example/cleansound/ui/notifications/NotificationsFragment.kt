package com.example.cleansound.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansound.MainApplication
import com.example.cleansound.adapter.FavoriteTracksAdapter
import com.example.cleansound.adapter.TracksAdapter
import com.example.cleansound.databinding.FragmentNotificationsBinding
import com.example.cleansound.ui.auth.ProfileSetupViewModel
import com.example.cleansound.ui.auth.ProfileSetupViewModelFactory
import com.example.cleansound.ui.home.HomeFragmentDirections

class NotificationsFragment : Fragment() {

    private val favoriteViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : FavoriteTracksAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        favoriteViewModel.favoriteTracks.observe(viewLifecycleOwner) {favoriteTrack ->
            (binding.favoriteTrackList.adapter as FavoriteTracksAdapter).submitList(favoriteTrack)
        }
    }

    private fun setupRecyclerView() {
        binding.favoriteTrackList.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteTracksAdapter { trackId ->
            val action = NotificationsFragmentDirections.actionNavigationNotificationsToTrackDetailFragment(trackId)
            findNavController().navigate(action)
        }
        binding.favoriteTrackList.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}