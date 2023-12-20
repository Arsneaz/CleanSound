package com.example.cleansound.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansound.MainApplication
import com.example.cleansound.adapter.FavoriteTracksAdapter
import com.example.cleansound.databinding.FragmentFavoriteTracksBinding
import com.example.cleansound.viewmodel.ProfileSetupViewModel
import com.example.cleansound.viewmodelfactory.ProfileSetupViewModelFactory

class FavoriteTracksFragment : Fragment() {

    private val favoriteViewModel: ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentFavoriteTracksBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : FavoriteTracksAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        favoriteViewModel.favoriteTracks.observe(viewLifecycleOwner) {favoriteTrack ->
            (binding.rvFavoriteTracks.adapter as FavoriteTracksAdapter).submitList(favoriteTrack)
        }
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteTracks.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteTracksAdapter { trackId ->
            val action = FavoriteTracksFragmentDirections.actionNavigationSearchToTrackDetailFragment(trackId)
            findNavController().navigate(action)
        }
        binding.rvFavoriteTracks.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}