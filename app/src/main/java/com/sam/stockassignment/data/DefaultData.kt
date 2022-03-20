package com.sam.stockassignment.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DefaultData(
    @Json(name = "default_stocks") val stocks : @RawValue List<List<String>> = listOf()
): Parcelable