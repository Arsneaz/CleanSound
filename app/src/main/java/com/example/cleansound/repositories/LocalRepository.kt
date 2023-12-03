package com.example.cleansound.repositories

import androidx.lifecycle.LiveData
import com.example.cleansound.local.data.AppDatabase
import com.example.cleansound.local.model.Track
import com.example.cleansound.local.model.User
import com.example.cleansound.local.model.UserFavoriteTrackReference

class LocalRepository(private val appDatabase: AppDatabase) {
    suspend fun insertUser(user: User){
        appDatabase.userTrackDao().insert(user)
    }
    private suspend fun insertTrack(track: Track) {
        appDatabase.userTrackDao().insert(track)
    }
    suspend fun isFavoriteTrack(emailId: String, trackId: String) : Boolean {
        return appDatabase.userTrackDao().isFavorite(emailId, trackId) > 0
    }
    suspend fun insertFavoriteTrack(emailId: String, track: Track) {
        insertTrack(track)
        val favorite = UserFavoriteTrackReference(emailId, track.trackId)
        appDatabase.userTrackDao().insertFavoriteTrack(favorite)
    }
    suspend fun removeFavoriteTrack(emailId: String, track: Track) {
        appDatabase.userTrackDao().removeFavorite(emailId, track.trackId)
    }

    fun getFavoriteTracks(emailId: String) : LiveData<List<Track>> {
        return appDatabase.userTrackDao().getTracks(emailId)
    }

}