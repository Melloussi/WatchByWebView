package com.network.watchbywebview.DATA.Database.IntegratedDB

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteAssetHelper
    var db: SQLiteDatabase? = null
    var cursor: Cursor? = null

    init {
        openHelper = SqliteOpenHelper(context)
    }

    fun open() {
        db = openHelper.writableDatabase
    }

    fun close() {
        if (db != null) {
            db!!.close()
        }
    }

    companion object {
        private var instance: DatabaseAccess? = null
        fun getInstance(context: Context): DatabaseAccess? {

            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }


}