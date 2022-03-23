package com.sam.stockassignment.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sam.stockassignment.MyApplication
import com.sam.stockassignment.R
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockWholeData
import dagger.hilt.android.internal.managers.ViewComponentManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
            id = it?.get(0) ?: "", name = it?.get(1) ?: "",
            yesterdayPrice = it?.get(7).changeDouble(), currentPrice = it?.get(7).changeDouble()
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

fun View.startFlicker() {
    isVisible = true
    val anim = AnimationUtils.loadAnimation(MyApplication.appContext, R.anim.an_flicker)
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}
        override fun onAnimationRepeat(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {
            isVisible = false
        }
    })
    this.startAnimation(anim)
}

@SuppressLint("SimpleDateFormat")
fun Long.toYrMonDayStr(): String {
    val c = Calendar.getInstance()
    c.timeInMillis = this
    val fr = SimpleDateFormat("yyyy-MM-dd")
    return fr.format(c.time)
}