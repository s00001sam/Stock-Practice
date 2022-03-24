package com.sam.stockassignment.view.helper

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class DataItemTouchListener : RecyclerView.OnItemTouchListener {
    private var touchY = 0f

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                rv.stopScroll()
                touchY = e.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (abs(e.y - touchY) > 5f) {
                    rv.parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP -> {
                rv.parent.requestDisallowInterceptTouchEvent(false)
                rv.clearFocus()
            }
        }
        return false
    }
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}