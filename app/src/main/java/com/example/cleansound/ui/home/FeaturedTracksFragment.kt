package com.example.cleansound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansound.MainApplication
import com.example.cleansound.adapter.FeaturedTracksDetailAdapter
import com.example.cleansound.databinding.FragmentFeaturedTracksBinding

class FeaturedTracksFragment : Fragment() {
    private val spotifyViewModel: HomeViewModel by viewModels {
        val spotifyRepository = (requireActivity().application as MainApplication).spotifyRepository
        SpotifyViewModelFactory(spotifyRepository)
    }

    private var _binding: FragmentFeaturedTracksBinding? = null
    private val binding get() = _binding!!
    private val args: FeaturedTracksFragmentArgs by navArgs()
    private lateinit var featuredTracksDetailAdapter : FeaturedTracksDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFeaturedTracksBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlistId = args.playlistId
        setupRecyclerView()
        println(playlistId)

        spotifyViewModel.getFeaturedTracks(playlistId)
        spotifyViewModel.featuredTracks.observe(viewLifecycleOwner) {tracks ->
            (binding.rvTracksPlaylist.adapter as FeaturedTracksDetailAdapter).submitList(tracks)
        }


    }

    private fun setupRecyclerView() {
        featuredTracksDetailAdapter = FeaturedTracksDetailAdapter {trackId ->
            val action = FeaturedTracksFragmentDirections.actionFeaturedTracksFragmentToTrackDetailFragment(trackId)
            findNavController().navigate(action)
        }
        binding.rvTracksPlaylist.adapter = featuredTracksDetailAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}