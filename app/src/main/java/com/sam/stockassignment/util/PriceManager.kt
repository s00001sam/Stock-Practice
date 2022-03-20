package com.sam.stockassignment.util

import com.sam.stockassignment.data.DefaultData
import com.sam.stockassignment.data.StockLocalData
import kotlin.random.Random

object PriceManager {

    var defaultStockIds : List<String>? = null

    var currentStocks: List<StockLocalData> = listOf()

    var interval: Long = 2000L

    fun randomUpdateStocks() {
        val changeIndexs = getRandomList()
        currentStocks.apply {
            mapIndexed { index, stockLocalData ->
                if (changeIndexs.contains(index)) {
                    stockLocalData.updateTime = System.currentTimeMillis()
                    stockLocalData.currentPrice = stockLocalData.yesterdayPrice.getRandomPrice()
                }
                stockLocalData
            }
        }
    }

    private fun getRandomList(): List<Int> {
        if (currentStocks.isEmpty()) return listOf()
        val number = currentStocks.size
        val count = Random.nextInt(1, number) //有幾檔需要變更
        return (currentStocks.indices).shuffled().take(count).sorted() //需要變更之位置清單
    }

    fun Double.getRandomPrice(): Double {
        return this * Random.nextDouble(0.9, 1.1)
    }

}