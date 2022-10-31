package com.network.watchbywebview.DATA.Requests

import com.network.watchbywebview.DATA.DataClasses.WDSource

interface SourceRequestInt {
   suspend fun getChannels(): MutableList<WDSource>?
}