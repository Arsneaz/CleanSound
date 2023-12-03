package com.example.cleansound.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    @ColumnInfo(name = "emailId")
    val emailId: String,

    @ColumnInfo(name = "name")
    val name: String
    // More Personal Information Later
)