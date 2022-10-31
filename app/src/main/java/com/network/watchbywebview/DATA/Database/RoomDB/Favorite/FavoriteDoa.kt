package com.network.watchbywebview.DATA.Database.RoomDB.Favorite

import androidx.room.*
import androidx.room.Dao

@Dao
interface FavoriteDoa {
    @Query("SELECT * FROM favorite_table")
    suspend fun getAll():MutableList<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE sourceUrl = :urlStream")
    suspend fun delete(urlStream: String)
}