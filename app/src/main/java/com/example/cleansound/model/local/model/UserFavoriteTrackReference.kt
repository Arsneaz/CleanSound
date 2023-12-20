package com.example.cleansound.model.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
@Entity(
    tableName = "user_favorite_tracks",
    primaryKeys = ["emailId", "trackId"],
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["emailId"], childColumns = ["emailId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Track::class, parentColumns = ["trackId"], childColumns = ["trackId"])
    ],
    indices = [Index("emailId"), Index("trackId")]
)
data class UserFavoriteTrackReference(
    @ColumnInfo(name = "emailId")
    val emailId : String,

    @ColumnInfo(name = "trackId")
    val trackId: String
)
