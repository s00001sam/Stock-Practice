package com.sam.stockassignment.repo

import com.sam.stockassignment.data.StockWholeData

interface Repository {
    suspend fun getStocks(): StockWholeData?
}