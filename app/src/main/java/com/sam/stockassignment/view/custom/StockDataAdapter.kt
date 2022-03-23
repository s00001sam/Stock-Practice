package com.sam.stockassignment.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sam.stockassignment.data.StockLocalData
import com.sam.stockassignment.data.StockLocalData.Companion.toListStr
import com.sam.stockassignment.databinding.ItemDataBinding
import com.sam.stockassignment.util.Logger

class StockDataAdapter() : ListAdapter<StockLocalData, StockDataAdapter.ViewHolder>(DiffCallback) {

    private var redList: MutableList<Int> = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: StockLocalData, redList: MutableList<Int>) {
            val adapter = StockDataItemAdapter()
            binding.rcyDataItem.adapter = adapter
            adapter.setQuote(stock.quote())
            adapter.submitList(stock.toListStr())

            if (redList.contains(adapterPosition)) {
                adapter.setNeedRed(true)
                redList.remove(adapterPosition)
                Logger.d("sam00 data redList=$redList")
            } else {
                adapter.setNeedRed(false)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDataBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<StockLocalData>() {
        override fun areItemsTheSame(oldItem: StockLocalData, newItem: StockLocalData): Boolean {
            return oldItem.currentPrice == newItem.currentPrice
        }

        override fun areContentsTheSame(oldItem: StockLocalData, newItem: StockLocalData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock, redList)
    }

    fun setRedList(redList: List<Int>) {
        val mutable = mutableListOf<Int>()
        mutable.addAll(redList)
        this.redList = mutable
    }
}