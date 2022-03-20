package com.sam.stockassignment.repo.datasource

import com.sam.stockassignment.data.StockWholeData

interface DataSource {
    suspend fun getStocks(): StockWholeData?
}