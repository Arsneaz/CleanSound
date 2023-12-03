package com.example.cleansound.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cleansound.MainApplication
import com.example.cleansound.databinding.FragmentTrackDetailBinding
import com.example.cleansound.local.model.Track
import com.example.cleansound.ui.auth.ProfileSetupViewModel
import com.example.cleansound.ui.auth.ProfileSetupViewModelFactory
import com.example.cleansound.ui.home.HomeViewModel
import com.example.cleansound.ui.home.SpotifyViewModelFactory

class TrackDetailFragment : Fragment() {
    private val spotifyViewModel : HomeViewModel by viewModels {
        val spotifyRepository = (requireActivity().application as MainApplication).spotifyRepository
        SpotifyViewModelFactory(spotifyRepository)
    }

    private val favoriteViewModel : ProfileSetupViewModel by viewModels {
        val localRepository = (requireActivity().application as MainApplication).localRepository
        ProfileSetupViewModelFactory(localRepository)
    }

    private var _binding: FragmentTrackDetailBinding? = null
    private val binding get() = _binding!!

    private val args: TrackDetailFragmentArgs by navArgs()
    private lateinit var trackEntities: Track

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackId = args.trackId
        spotifyViewModel.getSingleTrack(trackId)

        spotifyViewModel.singleTrack.observe (viewLifecycleOwner){track ->
            if (track != null) {
                trackEntities = Track(
                    trackId = track.id!!,
                    name = track.name!!,
                    artistNames = track.artists?.joinToString(", ") { artist -> artist?.name!! }!!,
                    imageUrl = track.album?.images?.firstOrNull()?.url ?: ""
                )
                binding.trackTitle.text = track.name
                Glide.with(binding.root.context).load(track.album?.images?.firstOrNull()?.url).into(binding.trackImage)

                // Move these inside the observe block
                favoriteViewModel.checkIfFavorite(trackEntities)
                binding.addIntoFavorite.setOnClickListener {
                    favoriteViewModel.toggleFavoriteStatus(trackEntities)
                }
            }
        }

        favoriteViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            binding.addIntoFavorite.text = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}