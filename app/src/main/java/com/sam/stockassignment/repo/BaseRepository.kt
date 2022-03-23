package com.sam.stockassignment.repo

import androidx.lifecycle.LiveData
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.repo.datasource.DataSource

class BaseRepository (
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource,
) : Repository {
    override suspend fun getStocks(): StockWholeData? = remoteDataSource.getStocks()
    override fun getAll(): LiveData<List<StockLocalData>> = localDataSource.getAll()
    override suspend fun insertStocks(list: List<StockLocalData>) = localDataSource.insertStocks(list)
}