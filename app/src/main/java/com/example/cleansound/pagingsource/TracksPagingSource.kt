package com.example.cleansound.pagingsource

// We use the pagination with the room, adios using this later I think


import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cleansound.model.Track
import com.example.cleansound.repositories.SpotifyRepository
import com.example.cleansound.spotify.SpotifyService
import java.io.IOException

//class TracksPagingSource(val spotifyService: SpotifyService, val playlistId: String) : PagingSource<Int, Track>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
//                closestPage.prevKey?.plus(1) ?: closestPage.nextKey?.minus(1)
//            }
//        }
//    }
//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
//        val offset = params.key ?: 0
//
//        return try {
//            val response = spotifyService.getPlaylistTracks(playlistId, params.loadSize, offset)
//            if (response.isSuccessful) {
//                val tracks = response.body()?.items?.mapNotNull { it?.track } ?: emptyList()
//
//                val nextOffset = offset + tracks.size
//
//                LoadResult.Page(
//                    data = tracks,
//                    prevKey = if (offset == 0) null else offset - params.loadSize,
//                    nextKey = nextOffset
//                )
//            } else {
//                LoadResult.Error(Exception("API Error: ${response.code()}"))
//            }
//        } catch (e: IOException) {
//            // IOException for network failures.
//            return LoadResult.Error(e)
//        } catch (e: HttpException) {
//            // HttpException for any non-2xx HTTP status codes.
//            return LoadResult.Error(e)
//        }
//
//    }
//
//}