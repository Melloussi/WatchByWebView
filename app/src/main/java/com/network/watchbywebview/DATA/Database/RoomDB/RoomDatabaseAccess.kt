package com.network.watchbywebview.DATA.Database.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteDoa
import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class RoomDatabaseAccess: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDoa

    companion object{
        @Volatile
        private var INSTANCE: RoomDatabaseAccess? = null

        fun getDatabase(context: Context):RoomDatabaseAccess{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseAccess::class.java,
                    "database"
                ).build()
                INSTANCE = instant
                return instant
            }
        }
    }
}