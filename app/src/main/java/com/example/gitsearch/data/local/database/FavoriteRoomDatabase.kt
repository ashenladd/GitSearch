package com.example.gitsearch.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitsearch.data.local.dao.FavoriteUserDao
import com.example.gitsearch.data.local.entity.FavoriteUser

@Database(
    entities = [FavoriteUser::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object{
        private const val DATABASE_NAME = "favorite_user_database"

        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null){
                synchronized(FavoriteRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteRoomDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }
}