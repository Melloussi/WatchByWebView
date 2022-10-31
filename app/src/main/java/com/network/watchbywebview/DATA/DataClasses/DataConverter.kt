package com.network.watchbywebview.DATA.DataClasses

import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteEntity

class DataConverter {
    fun convert(entity:MutableList<FavoriteEntity>):MutableList<WDSource>{
        return entity.map { it.toWDSource() }.toMutableList()
    }
    fun convertWDSourceToFavoriteEntity(favorite:WDSource):FavoriteEntity{
        return favorite.toFavoriteEntity()
    }


   private fun FavoriteEntity.toWDSource() = WDSource(
        sourceName = sourceName,
        iconUrl = iconUrl,
        sourceUrl = sourceUrl
    )

    fun WDSource.toFavoriteEntity() = FavoriteEntity(
        id = 0,
        sourceName = sourceName,
        iconUrl = iconUrl,
        sourceUrl = sourceUrl
    )
}