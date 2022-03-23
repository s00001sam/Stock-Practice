package com.sam.stockassignment.repo.datasource

import androidx.lifecycle.LiveData
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.repo.api.Api

class RemoteDataSource(private  val api: Api) : DataSource {
    override suspend fun getStocks(): StockWholeData? = api.getStocks()

    override fun getAll(): LiveData<List<StockLocalData>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertStocks(list: List<StockLocalData>) : List<Long> {
        TODO("Not yet implemented")
    }
}