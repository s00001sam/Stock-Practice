package com.sam.stockassignment.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sam.stockassignment.R
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.databinding.LayoutStockBinding
import com.sam.stockassignment.view.helper.DataItemTouchListener
import com.sam.stockassignment.view.helper.SyncRecyclerViewHelper
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Field
import javax.inject.Inject


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

    init {
        initView()
    }

    private fun initView() {
        syncRecyclerViewHelper.setRecyclerViews(listOf(binding.rcyName, binding.rcyData))
        binding.rcyData.isNestedScrollingEnabled = false
        binding.rcyData.addOnItemTouchListener(DataItemTouchListener())
        binding.rcyName.adapter = nameAdapter
        binding.rcyData.adapter = dataAdapter
        improveRecyckerViewSensitivity()

        val titleList = resources.getStringArray(R.array.title_array).toList()
        binding.rcyTitle.adapter = titleAdapter
        titleAdapter.submitList(titleList)
    }

    private fun improveRecyckerViewSensitivity() {
        try {
            val recyclerView = binding.rcyData
            val touchSlopField: Field = RecyclerView::class.java.getDeclaredField("mTouchSlop")
            touchSlopField.isAccessible = true
            val touchSlop = touchSlopField.get(recyclerView) as Int
            touchSlopField.set(recyclerView, touchSlop * 0.1f)
        } catch (e: Exception) {
            Log.d("sam","improveRecyckerViewSensitivity e=${e.message}")
        }
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