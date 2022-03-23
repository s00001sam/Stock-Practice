package com.sam.stockassignment.view.custom

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sam.stockassignment.R
import com.sam.stockassignment.databinding.ItemStockNameBinding
import com.sam.stockassignment.util.PriceManager
import com.sam.stockassignment.util.startFlicker

class StockDataItemAdapter() : ListAdapter<String, StockDataItemAdapter.ViewHolder>(DiffCallback) {

    private var quote: Double = 0.0
    private var needRed: Boolean = false

    fun setQuote(quote: Double) {
        this.quote = quote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemStockNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, quote: Double, needRed: Boolean) {
            val color = when {
                quote > 0.0 -> R.color.green
                quote < 0.0 -> R.color.red
                else -> R.color.white
            }
            binding.tvName.setTextColor(ContextCompat.getColor(binding.root.context, color))
            binding.name = name

            if (needRed && PriceManager.isShowRed) {
                binding.vRed.startFlicker()
            } else {
                binding.vRed.isVisible = false
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStockNameBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = getItem(position)
        holder.bind(name, quote, needRed)
    }

    fun setNeedRed(isNeed: Boolean) {
        needRed = isNeed
    }
}