package com.sam.stockassignment

import android.app.Application
import android.content.Context
import com.sam.stockassignment.util.AssetsUtil
import com.sam.stockassignment.util.PriceManager
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        var INSTANCE: MyApplication by Delegates.notNull()
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appContext = applicationContext
        PriceManager.defaultStockIds = AssetsUtil.getAssetsData()?.stocks?.map { it[0] ?: "" }
    }

}