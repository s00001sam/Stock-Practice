package com.sam.stockassignment.repo.datasource

import androidx.lifecycle.LiveData
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData
import com.sam.stockassignment.room.RoomDB

class LocalDataSource(private val roomDB: RoomDB) : DataSource {
    override suspend fun getStocks(): StockWholeData? {
        TODO("Not yet implemented")
    }

    override fun getAll(): LiveData<List<StockLocalData>> = roomDB.myDao().getAll()

    override suspend fun insertStocks(list: List<StockLocalData>) = roomDB.myDao().insertList(list)

}