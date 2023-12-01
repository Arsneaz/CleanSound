package com.example.cleansound.local.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleansound.local.model.Track

@Database(entities = [Track::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun trackDao(): TrackDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "track.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }

        }
    }

}