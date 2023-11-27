package com.example.cleansound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansound.MainApplication
import com.example.cleansound.R
import com.example.cleansound.adapter.TracksAdapter
import com.example.cleansound.databinding.FragmentHomeBinding
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.ui.auth.AuthViewModel
import com.example.cleansound.ui.auth.AuthViewModelFactory
import com.example.cleansound.ui.auth.SpotifyViewModelFactory

class HomeFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private val spotifyViewModel: HomeViewModel by viewModels {
        val spotifyRepository = (requireActivity().application as MainApplication).spotifyRepository
        SpotifyViewModelFactory(spotifyRepository)
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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        spotifyViewModel.fetchPlaylist()
        spotifyViewModel.playlist.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                val playlists = result.getOrNull()
                println("Playlist size ${playlists?.playlists?.items?.size}")
                playlists?.playlists?.items?.forEach { playlistItem ->
                    println("Playlist Name: ${playlistItem?.name}")
                    println("Playlist URL:  ${playlistItem?.tracks?.href}")
                    playlistItem?.images?.forEach { image ->
                        println("Image URL: ${image?.url}")
                    }
                }
                    println("--------------------------------------------------")
                } else {
                val exception = result.exceptionOrNull()
                println("Error fetching playlists: ${exception?.message}")
            }

        }

        spotifyViewModel.tracks.observe(viewLifecycleOwner) {tracks ->
            println("Total Tracks ${tracks.size}")
            (binding.trackList.adapter as TracksAdapter).submitList(tracks)
        }

        spotifyViewModel.fetchPlaylistTracks()
        // Log Button
//        binding.signOutBtn.setOnClickListener{
//            findNavController().navigate(R.id.action_navigation_home_to_loginFragment)
//            viewModel.signOut()
//        }
    }

    private fun setupRecyclerView() {
        binding.trackList.layoutManager = LinearLayoutManager(context)
        binding.trackList.adapter = TracksAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}