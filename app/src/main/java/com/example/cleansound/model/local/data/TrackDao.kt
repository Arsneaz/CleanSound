package com.example.cleansound.model.local.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleansound.model.local.model.Track

/**
 * Interface for setting up the Pagination stuff ayaya
 **/
@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): PagingSource<Int, Track>

    @Query("DELETE FROM tracks WHERE trackId NOT IN (SELECT trackId FROM user_favorite_tracks)")
    suspend fun clearNonFavoriteTracks()
}