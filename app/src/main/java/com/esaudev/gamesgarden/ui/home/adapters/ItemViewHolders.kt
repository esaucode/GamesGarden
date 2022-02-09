package com.esaudev.gamesgarden.ui.home.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.gamesgarden.databinding.FirstDataBinding
import com.esaudev.gamesgarden.databinding.SecondDataBinding
import com.esaudev.gamesgarden.model.DifferentItem

abstract class ItemViewHolders<in T>(itemView: View): RecyclerView.ViewHolder(itemView)

class FirstViewHolder(
    private val dataBinding: FirstDataBinding
): ItemViewHolders<DifferentItem>(dataBinding.root) {
    fun bind(item: DifferentItem) {
        dataBinding.item = item
    }
}

class SecondViewHolder(
    private val dataBinding: SecondDataBinding
): ItemViewHolders<DifferentItem>(dataBinding.root) {
    fun bind(item: DifferentItem) {
        dataBinding.item = item
    }
}


