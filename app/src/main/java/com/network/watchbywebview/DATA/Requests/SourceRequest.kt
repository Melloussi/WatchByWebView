package com.network.watchbywebview.DATA.Requests

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.network.watchbywebview.Config.Config
import com.network.watchbywebview.DATA.DataClasses.WDSource
import okhttp3.OkHttpClient
import okhttp3.Request


class SourceRequest: SourceRequestInt {

    override suspend fun getChannels(): MutableList<WDSource>? {
        val gson = Gson()

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Config().SOURCE_URL)
            .build()

        client.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                val body = response.body!!.string()
                gson.fromJson(body, object : TypeToken<MutableList<WDSource>>() {}.type)
            } else {
                null
            }
        }
    }

}