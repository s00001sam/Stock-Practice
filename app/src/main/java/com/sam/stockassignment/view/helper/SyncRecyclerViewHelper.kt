package com.sam.stockassignment.view.helper

import androidx.recyclerview.widget.RecyclerView

class SyncRecyclerViewHelper {
    private var syncRecyclers: List<RecyclerView> = listOf()
    private var isProgrammaticallyScrolling = false

    fun setRecyclerViews(syncRecyclers: List<RecyclerView>) {
        this.syncRecyclers = syncRecyclers
        for (recyclerView in syncRecyclers) {
            recyclerView.addOnScrollListener(syncOnScrollListener)
        }
    }

    private val syncOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!isProgrammaticallyScrolling) {
                isProgrammaticallyScrolling = true
                scrollAll(recyclerView, dx, dy)
                isProgrammaticallyScrolling = false
            }
        }
    }

    private fun scrollAll(exceptRecycler: RecyclerView, dx: Int, dy: Int) {
        for (recyclerView in syncRecyclers) {
            if (recyclerView != exceptRecycler) {
                recyclerView.scrollBy(dx, dy)
            }
        }
    }

}