package com.iqbalmungid.githubuserapi.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iqbalmungid.githubuserapi.data.local.entity.FavEntity

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase : RoomDatabase(){

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var instance: FavDatabase? = null
        fun getInstance(context: Context): FavDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java, "fav_database.db"
                ).build()
            }
    }
}