package com.sam.stockassignment.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.sam.stockassignment.R
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.databinding.LayoutStockBinding
import com.sam.stockassignment.util.getWrapperContextLifecycleOwner
import dagger.hilt.android.scopes.ViewScoped

@ViewScoped
class StockView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var w = 0
    private var h = 0
    private var showLoading: ((show: Boolean)->Unit)? = null

    private val lifecycleOwner: LifecycleOwner by lazy {
        getWrapperContextLifecycleOwner(context)
    }

    private val viewModel: CustomViewModel by lazy {
        ViewModelProvider(getWrapperContextLifecycleOwner(context) as FragmentActivity).get(
            CustomViewModel::class.java
        )
    }

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
        binding.rcyName.adapter = nameAdapter
        binding.rcyData.adapter = dataAdapter

        val titleList = resources.getStringArray(R.array.title_array).toList()
        binding.rcyTitle.adapter = titleAdapter
        titleAdapter.submitList(titleList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToView(list: List<StockLocalData>) {
        nameAdapter.submitList(list.map { it.name })
        nameAdapter.notifyDataSetChanged()
        dataAdapter.submitList(list)
        dataAdapter.notifyDataSetChanged()
    }

}