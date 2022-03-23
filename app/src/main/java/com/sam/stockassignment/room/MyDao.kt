package com.sam.stockassignment.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sam.stockassignment.data.StockLocalData

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<StockLocalData>): List<Long>

    @Query("DELETE FROM stock_table")
    suspend fun clear()

    @Query("SELECT * FROM stock_table")
    fun getAll(): LiveData<List<StockLocalData>>

}