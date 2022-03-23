package com.sam.stockassignment.util

import android.content.Context
import com.sam.stockassignment.MyApplication

object SpUtil {
    private const val STOCK_SP = "STOCK_SP"
    const val KEY_STOCKS = "KEY_STOCKS"

    private val sp = MyApplication.appContext.getSharedPreferences(STOCK_SP, Context.MODE_PRIVATE)

    fun putString(key: String, s: String) {
        MyApplication.appContext
            .getSharedPreferences(STOCK_SP, Context.MODE_PRIVATE)
            .edit().putString(key, s)
            .apply()
    }

    fun getString(key: String): String? {
        return sp.getString(key, null)
    }
}