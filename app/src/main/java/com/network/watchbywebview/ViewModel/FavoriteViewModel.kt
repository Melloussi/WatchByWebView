package com.network.watchbywebview.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteEntity
import com.network.watchbywebview.DATA.Repo.FavoriteRepo

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repo = FavoriteRepo(application)
    private val result = MutableLiveData<MutableList<WDSource>>()
    val getAll = result

    fun getFavorite(){
        result.value = repo.getFavorite()
    }
    fun insertFavorite(favorite:WDSource){
        repo.insertFavorite(favorite)
    }
    fun deleteFavorite(favorite: FavoriteEntity){
        repo.deleteFavorite(favorite)
    }
}