package com.example.cleansound.local.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    @ColumnInfo(name = "emailId")
    val emailId: String,

    @ColumnInfo(name = "name")
    val name: String?,

    val desc: String?,

    val imagePath: String?
)