package com.network.watchbywebview.DATA.Database.IntegratedDB

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class SqliteOpenHelper (context: Context?) : SQLiteAssetHelper(context, DTATABASE_NAME, null, DTATABASE_VERSION) {
    companion object {
        private const val DTATABASE_NAME = "Ads_Black_List.db"//databases/
        private const val DTATABASE_VERSION = 1
    }
}