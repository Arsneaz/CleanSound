package com.example.cleansound.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "update_info")
data class UpdateInfo(
    @PrimaryKey
    @ColumnInfo(name = "key")
    val `key`: String,

    @ColumnInfo(name = "lastUpdateTime")
    val lastUpdateTime: Long
)
