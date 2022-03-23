package com.sam.stockassignment.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.stockassignment.util.Util.getTimeStr
import com.sam.stockassignment.util.to2fString
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stock_table")
@Parcelize
data class StockLocalData (
    @PrimaryKey(autoGenerate = true)
    var roomId: Int = 0,
    var id: String = "",
    var name: String = "",
    var yesterdayPrice: Double = 0.0,
    var currentPrice: Double = 0.0,
    var updateTime: Long = 0L
): Parcelable {

    companion object {
        fun StockLocalData.toListStr() : List<String> =
            listOf(currentPriceStr(), quoteStr(), changeRangeStr(), yesterdayPriceStr(), updateTimeStr())
    }

    fun currentPriceStr() = currentPrice.to2fString()

    fun yesterdayPriceStr() = yesterdayPrice.to2fString()

    fun quote() = currentPrice - yesterdayPrice

    fun quoteStr() = quote().to2fString()

    fun changeRangeStr(): String {
        val change = (currentPrice - yesterdayPrice)/yesterdayPrice * 100
        return "${change.to2fString()}%"
    }

    fun updateTimeStr() = updateTime.getTimeStr()

}