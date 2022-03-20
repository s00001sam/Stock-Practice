package com.sam.stockassignment.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class StockWholeData(
    val data: @RawValue List<List<String>?>? = null,
    val date: String? = null,
    val fields: @RawValue List<String>? = null,
    val notes: @RawValue List<String>? = null,
    val stat: String? = null,
    val title: String? = null
) : Parcelable {

    fun isSuccess() = stat == "OK"
}