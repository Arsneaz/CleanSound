package com.example.cleansound.local.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cleansound.local.model.Track
import com.example.cleansound.local.model.User
import com.example.cleansound.local.model.UserFavoriteTrackReference

@Dao
interface UserTrackDao {
    // User inserting operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Track inserting operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteTrack(crossRef: UserFavoriteTrackReference)

    @Query("SELECT COUNT(*) FROM user_favorite_tracks WHERE emailId = :emailId and trackId = :trackId")
    suspend fun isFavorite(emailId: String, trackId: String) : Int

    @Query("DELETE FROM user_favorite_tracks WHERE emailId = :emailId and trackId = :trackId")
    suspend fun removeFavorite(emailId: String, trackId: String)

    @Transaction
    @Query("SELECT * FROM tracks INNER JOIN user_favorite_tracks ON tracks.trackId = user_favorite_tracks.trackId WHERE user_favorite_tracks.emailId = :emailId")
    fun getTracks(emailId : String) : LiveData<List<Track>>

}