package com.network.watchbywebview.DATA.Repo

import android.app.Application
import com.network.watchbywebview.DATA.Database.IntegratedDB.ExecuteSQLCommand.SqliteExecuteCommand
import kotlinx.coroutines.*

class WebViewRepo (application: Application){
    private val db = SqliteExecuteCommand(application)

    fun checkHost(host:String):Boolean{
        val job = CoroutineScope(Dispatchers.IO).async {
            db.checkHost(host)
        }

        val result:Boolean = runBlocking {
            //db.checkHost(host)
            job.await()
        }

        return result
    }
}