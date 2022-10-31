package com.network.watchbywebview.DATA.Database.IntegratedDB.ExecuteSQLCommand

import android.annotation.SuppressLint
import android.content.Context
import com.network.watchbywebview.DATA.Database.IntegratedDB.DatabaseAccess

class SqliteExecuteCommand (context: Context) : SqliteExecuteCommandInt {

    private var dataAccess: DatabaseAccess = DatabaseAccess.getInstance(context)!!

    init {
        dataAccess.open()
    }

        @SuppressLint("Range")
        override suspend fun checkHost(host: String): Boolean {
            val sql = "SELECT url FROM ads_url_list WHERE url like \'$host\'"



            val cursor = dataAccess.db!!.rawQuery(sql, null)

            println("Host Existed?: ${cursor.moveToFirst()} Host: $host")

            return cursor != null && cursor.moveToFirst()
    }

    override fun close() {
        dataAccess.close()
    }
    fun open(){
        dataAccess.open()
    }
}