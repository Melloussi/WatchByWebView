package com.network.watchbywebview.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.network.watchbywebview.DATA.DataClasses.WDSource

class SearchResultVM : ViewModel() {
    private val searchResult = MutableLiveData<MutableList<WDSource>>()
    val getAllResult = searchResult

    fun setResult(resultList:MutableList<WDSource>){

//        if (searchResult.value != null && searchResult.value!!.isNotEmpty()){
//            searchResult.value!!.clear()
//        }
        //searchResult.value!!.clear()
        searchResult.value = resultList
        println("SearchMV: ${getAllResult.value?.size}")
    }

}