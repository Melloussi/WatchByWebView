package com.network.watchbywebview.DATA.Repo

import android.app.Application
import com.network.watchbywebview.DATA.DataClasses.DataConverter
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteEntity
import com.network.watchbywebview.DATA.Database.RoomDB.RoomDatabaseAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FavoriteRepo(application: Application) {
    private val dbAccess = RoomDatabaseAccess.getDatabase(application)
    private val dc = DataConverter()

    fun getFavorite():MutableList<WDSource>{
        val job = CoroutineScope(Dispatchers.IO).async {
            dbAccess.favoriteDao().getAll()
        }
        return runBlocking {
            dc.convert(job.await())
        }
    }

    fun insertFavorite(favorite: WDSource){
        CoroutineScope(Dispatchers.IO).async {
            dbAccess.favoriteDao().insert(dc.convertWDSourceToFavoriteEntity(favorite))
        }
    }

    fun deleteFavorite(favorite: FavoriteEntity){
        CoroutineScope(Dispatchers.IO).async {
            dbAccess.favoriteDao().delete(favorite.sourceUrl)
            //dc.convertWDSourceToFavoriteEntity(favorite)
        }
    }
}