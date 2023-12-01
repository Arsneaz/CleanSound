package com.example.cleansound.local.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleansound.local.model.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): PagingSource<Int, Track>

    @Query("DELETE FROM tracks")
    suspend fun clearTracks()
}