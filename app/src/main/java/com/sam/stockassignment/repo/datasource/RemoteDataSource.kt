package com.sam.stockassignment.repo.datasource

import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.repo.api.Api

class RemoteDataSource(private  val api: Api) : DataSource {
    override suspend fun getStocks(): StockWholeData? = api.getStocks()
}