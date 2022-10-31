package com.network.watchbywebview.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.Repo.SourceRequestRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WDSourceVM: ViewModel() {
    private val repo = SourceRequestRepo()
    private val result = MutableLiveData<MutableList<WDSource>>()
    val getAll = result

    fun getSource(){
        runBlocking {
            result.value = repo.getChannels()
        }
    }
}