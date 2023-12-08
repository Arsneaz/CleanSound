package com.example.cleansound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleansound.MainApplication
import com.example.cleansound.adapter.FeaturedPlaylistsAdapter
import com.example.cleansound.adapter.FeaturedTracksAdapter
import com.example.cleansound.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val spotifyViewModel: HomeViewModel by viewModels {
        val spotifyRepository = (requireActivity().application as MainApplication).spotifyRepository
        SpotifyViewModelFactory(spotifyRepository)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var featuredTracksAdapter : FeaturedTracksAdapter
    private lateinit var featuredPlaylistsAdpter : FeaturedPlaylistsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        spotifyViewModel.fetchPlaylist()
        spotifyViewModel.playlist.observe(viewLifecycleOwner) { playlist ->
            (binding.rvFeaturedPlaylist.adapter as FeaturedPlaylistsAdapter).submitList(playlist)

        }


        spotifyViewModel.tracks.observe(viewLifecycleOwner) { pagingData ->
            (binding.rvFeaturedTracks.adapter as FeaturedTracksAdapter).submitData(lifecycle, pagingData)
        }
    }

    private fun setupRecyclerView() {
        featuredTracksAdapter = FeaturedTracksAdapter { trackId ->
            val action = HomeFragmentDirections.actionNavigationHomeToTrackDetailFragment(trackId)
            findNavController().navigate(action)
        }
        binding.rvFeaturedTracks.adapter = featuredTracksAdapter

        featuredPlaylistsAdpter = FeaturedPlaylistsAdapter { playlistId ->
            val action = HomeFragmentDirections.actionNavigationHomeToFeaturedTracksFragment(playlistId)
            findNavController().navigate(action)
        }
        binding.rvFeaturedPlaylist.adapter = featuredPlaylistsAdpter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}