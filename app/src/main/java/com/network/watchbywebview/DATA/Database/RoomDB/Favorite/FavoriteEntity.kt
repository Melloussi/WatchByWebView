package com.network.watchbywebview.DATA.Database.RoomDB.Favorite

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table", indices = [Index(value = ["sourceName","sourceUrl"], unique = true)])
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sourceName: String,
    val iconUrl: String,
    val sourceUrl: String
)