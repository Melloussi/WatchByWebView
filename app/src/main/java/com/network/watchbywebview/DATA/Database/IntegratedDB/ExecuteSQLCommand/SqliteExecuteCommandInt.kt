package com.network.watchbywebview.DATA.Database.IntegratedDB.ExecuteSQLCommand

interface SqliteExecuteCommandInt {
    suspend fun checkHost(host:String):Boolean
    fun close()
}