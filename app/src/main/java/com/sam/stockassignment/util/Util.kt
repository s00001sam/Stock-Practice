package com.sam.stockassignment.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast
import com.sam.stockassignment.MyApplication
import java.text.SimpleDateFormat
import java.util.*

object Util {

    fun String.showToast() {
        Toast.makeText(MyApplication.appContext, this, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.getTimeStr(): String {
        val c = Calendar.getInstance()
        c.timeInMillis = this
        val fr = SimpleDateFormat("HH:mm:ss")
        return fr.format(c.time)
    }
}