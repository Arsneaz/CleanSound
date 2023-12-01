package com.example.cleansound.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cleansound.local.data.AppDatabase
import com.example.cleansound.local.model.Track
import com.example.cleansound.model.FeaturedPlaylist
import com.example.cleansound.remotemediator.TracksRemoteMediator
import com.example.cleansound.spotify.SpotifyService
import kotlinx.coroutines.flow.Flow

class SpotifyRepository(private val spotifyService: SpotifyService, private val appDatabase: AppDatabase) {

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
    @OptIn(ExperimentalPagingApi::class)
    fun getTracksForPlaylist(): Flow<PagingData<com.example.cleansound.local.model.Track>> {
        println("It can be fetched")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = TracksRemoteMediator(spotifyService, appDatabase),
            pagingSourceFactory = { appDatabase.trackDao().getAllTracks() }
            ).flow

    }

//    suspend fun getFeaturedPlaylistTracks(): Result<List<Track>> {
//        return try {
//            val response = spotifyService.getFeaturedPlaylist()
//            if (!response.isSuccessful) {
//                return Result.failure(Exception("Failed to fetch featured playlists"))
//            }
//
//            val playlist = response.body()?.playlists?.items.orEmpty()
//
//            val allTracks = mutableListOf<Track>()
//            playlist.forEach { playlistItem ->
//                playlistItem?.id?.let { id ->
//                    val trackResponse = spotifyService.getPlaylistTracks(id)
//                    if (trackResponse.isSuccessful) {
//                        val tracks =
//                            trackResponse.body()?.items?.mapNotNull { it?.track } ?: emptyList()
//                        allTracks.addAll(tracks)
//                    }
//                }
//
//            }
//            Result.success(allTracks)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }


}