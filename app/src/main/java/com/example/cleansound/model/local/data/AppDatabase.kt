package com.example.cleansound.model.local.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleansound.model.local.model.Track
import com.example.cleansound.model.local.model.UpdateInfo
import com.example.cleansound.model.local.model.User
import com.example.cleansound.model.local.model.UserFavoriteTrackReference

@Database(
    entities = [Track::class, User::class, UserFavoriteTrackReference::class, UpdateInfo::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun trackDao(): TrackDao
    abstract fun userTrackDao() : UserTrackDao
    abstract fun RefreshStateDao() : RefreshStateDao
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