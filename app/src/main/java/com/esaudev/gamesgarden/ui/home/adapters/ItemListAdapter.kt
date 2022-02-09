package com.esaudev.gamesgarden.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.gamesgarden.databinding.FirstDataBinding
import com.esaudev.gamesgarden.databinding.SecondDataBinding
import com.esaudev.gamesgarden.model.DifferentItem
import com.esaudev.gamesgarden.util.ItemConstants.FIRST_ITEM_TYPE
import com.esaudev.gamesgarden.util.ItemConstants.SECOND_ITEM_TYPE

class ItemListAdapter: ListAdapter<DifferentItem, ItemViewHolders<*>>(DiffUtilCallback){

    private object DiffUtilCallback : DiffUtil.ItemCallback<DifferentItem>() {
        override fun areItemsTheSame(oldItem: DifferentItem, newItem: DifferentItem): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: DifferentItem, newItem: DifferentItem): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolders<*> {
        return when(viewType) {
            FIRST_ITEM_TYPE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = FirstDataBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                FirstViewHolder(dataBinding)
            }
            SECOND_ITEM_TYPE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = SecondDataBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                SecondViewHolder(dataBinding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolders<*>, position: Int) {
        when(holder) {
            is FirstViewHolder -> holder.bind(getItem(position))
            is SecondViewHolder -> holder.bind(getItem(position))
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type
}