package com.example.cleansound.remotemediator

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cleansound.local.data.AppDatabase
import com.example.cleansound.local.model.Track
import com.example.cleansound.spotify.SpotifyService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TracksRemoteMediator(
    private val spotifyService: SpotifyService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Track>(){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Track>): MediatorResult {
        println("Trying to fetch the data")
        try {
            // Decide when and what to fetch based on loadType
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null // Logic for initial load or refresh
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true) // No prepending in this scenario
                LoadType.APPEND -> { state.lastItemOrNull() }
            }

            // Fetch data from network
            println("inside the remote mediator")
            val response = spotifyService.getFeaturedPlaylist()
            val playlists = response.body()?.playlists?.items ?: emptyList()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.trackDao().clearTracks() // Clear old tracks on refresh
                }

                // Iterate over each playlist and fetch its tracks
                playlists.forEach{ playlist ->
                    val trackResponse = spotifyService.getPlaylistTracks(playlist?.id!!)
                    val tracks = trackResponse.body()?.items?.mapNotNull { it?.track } ?: emptyList()

                    //
                    val trackEntities = tracks.map { track ->
                        Track(
                            id = track.id!!,
                            name = track.name!!,
                            artistNames = track.artists?.joinToString(", ") { artist -> artist?.name!!}!!,
                            imageUrl = track.album?.images?.firstOrNull()?.url ?: ""
                        )
                    }
                    appDatabase.trackDao().insertAll(trackEntities)
                }
            }
            println("finished state")

        return MediatorResult.Success(endOfPaginationReached = playlists.isNotEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

}