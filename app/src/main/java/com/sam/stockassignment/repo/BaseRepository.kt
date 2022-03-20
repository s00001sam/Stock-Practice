package com.sam.stockassignment.repo

import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.repo.datasource.DataSource

class BaseRepository (
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource,
) : Repository {
    override suspend fun getStocks(): StockWholeData? = remoteDataSource.getStocks()
}