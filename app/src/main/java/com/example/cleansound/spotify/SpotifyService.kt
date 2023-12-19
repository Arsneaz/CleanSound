package com.example.cleansound.spotify

import com.example.cleansound.model.playlists.FeaturedPlaylists
import com.example.cleansound.model.search.SearchTrack
import com.example.cleansound.model.track.SingleTrack
import com.example.cleansound.model.tracks.PlaylistTracks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {
    @GET("browse/featured-playlists?country=ID&limit=5")
    suspend fun getFeaturedPlaylist() : Response<FeaturedPlaylists>

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") playlistId: String?,
    ) : Response<PlaylistTracks>

    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Query("limit") limit: Int = 20
    ) : Response<SearchTrack>

    @GET("tracks/{track_id}")
    suspend fun getTrack(
        @Path("track_id") trackId: String?
    ) : Response<SingleTrack>
}