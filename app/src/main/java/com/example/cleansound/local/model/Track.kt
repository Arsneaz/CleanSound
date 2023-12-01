package com.example.cleansound.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey val id: String,
    val name: String,
    val artistNames: String,
    val imageUrl: String
)
