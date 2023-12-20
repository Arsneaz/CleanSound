package com.example.cleansound.model.local.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleansound.model.local.model.UpdateInfo

@Dao
interface RefreshStateDao {
    @Query("SELECT lastUpdateTime FROM update_info WHERE `key` = :key")
    suspend fun getLastUpdateTime(key: String = "lastUpdateTime") : Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLastUpdateTime(updateInfo: UpdateInfo)
}