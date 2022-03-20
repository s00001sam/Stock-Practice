package com.sam.stockassignment.repo.datasource

import com.sam.stockassignment.data.StockWholeData

class LocalDataSource() : DataSource {
    override suspend fun getStocks(): StockWholeData? {
        TODO("Not yet implemented")
    }

}