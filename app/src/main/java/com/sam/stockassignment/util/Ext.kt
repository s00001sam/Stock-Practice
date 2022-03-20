package com.sam.stockassignment.util

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData
import dagger.hilt.android.internal.managers.ViewComponentManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectFlow(lifecycleOwner: LifecycleOwner, doSomething:((t: T)-> Unit)) {
    this.onEach {
        doSomething.invoke(it)
    }.launchWhenStarted(lifecycleOwner)
}

private fun <T> Flow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner)= with(lifecycleOwner) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            try {
                this@launchWhenStarted.collect()
            }catch (t: Throwable){
                Logger.d("launchWhenStarted throwable=${t.localizedMessage}")
            }
        }
    }
}

fun getWrapperContextLifecycleOwner(context: Context): LifecycleOwner {
    val lifecycleOwner: LifecycleOwner
    if (context is ViewComponentManager.FragmentContextWrapper) {
        lifecycleOwner = context.baseContext as LifecycleOwner
    } else {
        lifecycleOwner = context as LifecycleOwner
    }
    return lifecycleOwner
}

fun StockWholeData?.toStockLocalDatas(): List<StockLocalData>? {
    if (this?.data.isNullOrEmpty()) return null
    return this?.data?.map {
        StockLocalData(
            it?.get(0) ?: "", it?.get(1) ?: "", it?.get(7).changeDouble(), it?.get(7).changeDouble()
        )
    }
}

fun String?.changeDouble() : Double {
    var d : Double = 0.0
    try {
        d = this?.toDouble() ?: 0.0
    } catch (e: Exception) {

    }
    return d
}

fun Double.to2fString() = String.format("%.2f", this)