package com.sam.stockassignment.repo.datasource

import androidx.lifecycle.LiveData
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData

interface DataSource {
    suspend fun getStocks(): StockWholeData?
    fun getAll(): LiveData<List<StockLocalData>>
    suspend fun insertStocks(list: List<StockLocalData>) : List<Long>
}