package com.folioreader.Labu

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class LabuDBHelper(context: Context): SQLiteAssetHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "labu1.1.sqlite"
        private const val DB_VERSION = 1
    }

}