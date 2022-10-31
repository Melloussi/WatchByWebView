package com.network.watchbywebview.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.network.watchbywebview.DATA.Repo.WebViewRepo

class WebViewViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = WebViewRepo(application)
    private var _url = ""


    fun checkHost(host: String): Boolean {
        return repo.checkHost(host)
    }

    fun setUrlSource(url:String){
        _url = url
    }

    fun getUrlSource():String{
        return _url
    }
}