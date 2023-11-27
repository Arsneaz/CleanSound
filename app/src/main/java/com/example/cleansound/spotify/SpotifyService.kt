package com.example.cleansound.spotify

import com.example.cleansound.model.FeaturedPlaylist
import com.example.cleansound.model.PlaylistTrack
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotifyService {
    @GET("browse/featured-playlists")
    suspend fun getFeaturedPlaylist() : Response<FeaturedPlaylist>

    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(@Path("playlist_id") playlistId: String) : Response<PlaylistTrack>
}