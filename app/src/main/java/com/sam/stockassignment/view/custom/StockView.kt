package com.sam.stockassignment.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.sam.stockassignment.R
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.databinding.LayoutStockBinding
import com.sam.stockassignment.util.Logger
import com.sam.stockassignment.view.helper.SyncRecyclerViewHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs


@AndroidEntryPoint
class StockView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    @Inject
    lateinit var syncRecyclerViewHelper: SyncRecyclerViewHelper

    private var touchY = 0f
    private val binding: LayoutStockBinding by lazy {
        LayoutStockBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val nameAdapter: StockNameAdapter by lazy { StockNameAdapter() }
    private val titleAdapter: StockTitleAdapter by lazy { StockTitleAdapter() }
    private val dataAdapter: StockDataAdapter by lazy { StockDataAdapter() }

    private val rcyOnItemTouch: OnItemTouchListener by lazy {
        object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Logger.d("sam00 down=${e.y}")
                        touchY = e.y
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Logger.d("sam00 move=${e.y} down=${touchY}")
                        if (abs(e.y - touchY) > 10f) {
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                    MotionEvent.ACTION_UP -> rv.clearFocus()
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        }
    }

    init {
        initView()
    }

    private fun initView() {
        syncRecyclerViewHelper.setRecyclerViews(listOf(binding.rcyName, binding.rcyData))
        binding.rcyData.isNestedScrollingEnabled = false
        binding.rcyData.addOnItemTouchListener(rcyOnItemTouch)
        binding.rcyName.adapter = nameAdapter
        binding.rcyData.adapter = dataAdapter

        val titleList = resources.getStringArray(R.array.title_array).toList()
        binding.rcyTitle.adapter = titleAdapter
        titleAdapter.submitList(titleList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToView(list: List<StockLocalData>, items: List<Int>) {
        val visibles = items.getVisibleChangeItems()

        nameAdapter.submitList(list.map { it.name })
        nameAdapter.setRedList(visibles)
        nameAdapter.notifyDataSetChanged()

        dataAdapter.submitList(list)
        dataAdapter.setRedList(visibles)
        dataAdapter.notifyDataSetChanged()
    }

    private fun List<Int>.getVisibleChangeItems() =
        this.filter { it >= getFirstSeeItem() && it <= getLastSeeItem() }

    private fun getFirstSeeItem(): Int {
        val lm = binding.rcyData.layoutManager as LinearLayoutManager
        return lm.findFirstVisibleItemPosition()
    }

    private fun getLastSeeItem(): Int {
        val lm = binding.rcyData.layoutManager as LinearLayoutManager
        return lm.findLastVisibleItemPosition()
    }

}