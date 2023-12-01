package com.example.cleansound.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleansound.MainApplication
import com.example.cleansound.adapter.TracksAdapter
import com.example.cleansound.databinding.FragmentHomeBinding
import com.example.cleansound.repositories.AuthRepository
import com.example.cleansound.ui.auth.AuthViewModel
import com.example.cleansound.ui.auth.AuthViewModelFactory

class HomeFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(AuthRepository())
    }

    private val spotifyViewModel: HomeViewModel by activityViewModels {
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
        spotifyViewModel.playlist.observe(viewLifecycleOwner) { response ->
            println("Total playlist ${response?.playlists?.items?.size}")
            response?.playlists?.items?.forEach{
                println("Total playlist ${it?.name}")
            }
        }

        spotifyViewModel.tracks.observe(viewLifecycleOwner) { pagingData ->
            (binding.trackList.adapter as TracksAdapter).submitData(lifecycle, pagingData)
        }
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