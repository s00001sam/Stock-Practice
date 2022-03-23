package com.sam.stockassignment.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sam.stockassignment.databinding.ItemStockNameBinding
import com.sam.stockassignment.util.Logger
import com.sam.stockassignment.util.startFlicker

class StockNameAdapter() : ListAdapter<String, StockNameAdapter.ViewHolder>(DiffCallback) {

    private var redList: MutableList<Int> = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemStockNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, redList: MutableList<Int>) {
            binding.name = name
            if (redList.contains(adapterPosition)) {
                binding.vRed.startFlicker()
                redList.remove(adapterPosition)
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
        holder.bind(name, redList)
    }

    fun setRedList(redList: List<Int>) {
        val mutable = mutableListOf<Int>()
        mutable.addAll(redList)
        this.redList = mutable
    }
}