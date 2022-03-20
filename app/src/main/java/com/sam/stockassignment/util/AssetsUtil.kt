package com.sam.stockassignment.util

import com.sam.stockassignment.MyApplication
import com.sam.stockassignment.data.DefaultData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.io.InputStream

object AssetsUtil {

    private const val DEFAULT_STOCKS = "default_stocks.json"

    fun getAssetsData(strokeAssets: String = DEFAULT_STOCKS): DefaultData? {
        var stream: InputStream? = null
        var data : DefaultData? = null

        try {
            stream = MyApplication.appContext.assets.open(strokeAssets)
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val adapter: JsonAdapter<DefaultData> = moshi.adapter(DefaultData::class.java)
            val json = stream.bufferedReader().use { it.readText() }
            data =  adapter.fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.d("getAssetsData e=${e.localizedMessage}")
        } finally {
            if (stream != null) {
                try {
                    stream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return data
    }
}