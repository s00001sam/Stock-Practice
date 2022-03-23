package com.sam.stockassignment.repo.api

import com.sam.stockassignment.data.StockWholeData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("exchangeReport/STOCK_DAY_ALL")
    suspend fun getStocks(
        @Query("response") response: String? = "json"
    ): StockWholeData?

}