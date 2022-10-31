package com.network.watchbywebview.DATA.Repo

import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.Requests.SourceRequest
import com.network.watchbywebview.DATA.Requests.SourceRequestInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SourceRequestRepo: SourceRequestInt {

    private val sourceRequest = SourceRequest()

    override suspend fun getChannels(): MutableList<WDSource>? {
        val job = CoroutineScope(Dispatchers.IO).async {
            sourceRequest.getChannels()
        }
        return runBlocking {
            job.await()
        }
    }


}