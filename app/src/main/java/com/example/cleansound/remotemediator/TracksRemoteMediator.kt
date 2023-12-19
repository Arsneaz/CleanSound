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
import com.example.cleansound.local.model.UpdateInfo
import com.example.cleansound.spotify.SpotifyService
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class TracksRemoteMediator(
    private val spotifyService: SpotifyService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Track>(){

    /**
     * Time-based Refresh to prevent uncontrollable data fetching from the API
     * I set to 10 minutes interval cuz seems a rational I guess
     */
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MINUTES.toMillis(10)
        val lastUpdateTime = appDatabase.RefreshStateDao().getLastUpdateTime() ?: 0
        val currentTime = System.currentTimeMillis()

        return if (lastUpdateTime == 0L || currentTime - lastUpdateTime >= cacheTimeout) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Track>): MediatorResult {
        var successfulDataFetch = false

        try {
            // Fetch data from network
            val response = spotifyService.getFeaturedPlaylist()
            val playlists = response.body()?.playlists?.items ?: emptyList()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.trackDao().clearNonFavoriteTracks() // Clear old tracks that is non favorite
                }

                // Iterate over each playlist and fetch its tracks
                playlists.forEach{ playlist ->
                    val trackResponse = spotifyService.getPlaylistTracks(playlist?.id!!)
                    val tracks = trackResponse.body()?.items?.mapNotNull { it?.track } ?: emptyList()

                    //
                    val trackEntities = tracks.map { track ->
                        Track(
                            trackId = track.id!!,
                            name = track.name!!,
                            artistNames = track.artists?.joinToString(", ") { artist -> artist?.name!!}!!,
                            imageUrl = track.album?.images?.firstOrNull()?.url ?: ""
                        )
                    }
                    appDatabase.trackDao().insertAll(trackEntities)
                }
                successfulDataFetch = true
            }

            if (successfulDataFetch) {
                val currentTime = System.currentTimeMillis()
                val updateInfo = UpdateInfo(key = "lastUpdateTime", currentTime)
                appDatabase.RefreshStateDao().updateLastUpdateTime(updateInfo)
            }

        return MediatorResult.Success(endOfPaginationReached = playlists.isNotEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

}