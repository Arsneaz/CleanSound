package com.example.cleansound.repositories

import com.example.cleansound.model.FeaturedPlaylist
import com.example.cleansound.model.PlaylistTrack
import com.example.cleansound.model.Track
import com.example.cleansound.model.TrackItem
import com.example.cleansound.spotify.SpotifyService

class SpotifyRepository(private val spotifyService: SpotifyService) {

    suspend fun getFeaturedPlaylists(): Result<FeaturedPlaylist> {
        return try {
            val response = spotifyService.getFeaturedPlaylist()
            if (response.isSuccessful) {
                Result.success(response.body() ?: throw Exception("No playlists found"))
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFeaturedPlaylistTracks(): Result<List<Track>> {
        return try {
            val response = spotifyService.getFeaturedPlaylist()
            if (!response.isSuccessful) {
                return Result.failure(Exception("Failed to fetch featured playlists"))
            }

            val playlist = response.body()?.playlists?.items.orEmpty()

            val allTracks = mutableListOf<Track>()
            playlist.forEach { playlistItem ->
                playlistItem?.id?.let { id ->
                    val trackResponse = spotifyService.getPlaylistTracks(id)
                    if (trackResponse.isSuccessful) {
                        val tracks =
                            trackResponse.body()?.items?.mapNotNull { it?.track } ?: emptyList()
                        allTracks.addAll(tracks)
                    }
                }

            }
            Result.success(allTracks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}