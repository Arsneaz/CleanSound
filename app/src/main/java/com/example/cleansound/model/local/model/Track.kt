package com.example.cleansound.model.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey
    @ColumnInfo(name = "trackId")
    val trackId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "artistNames")
    val artistNames: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String


)
