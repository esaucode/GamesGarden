package com.esaudev.gamesgarden.ui.navigation_states.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.esaudev.gamesgarden.databinding.ItemFeatureBinding
import com.esaudev.gamesgarden.model.Player

class PlayerListAdapter(): ListAdapter<Player, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemFeatureBinding) : BaseListViewHolder<Player>(binding.root) {

        override fun bind(item: Player, position: Int) = with(binding) {

            tvFeature.text = item.name

            clFeature.setOnClickListener {
                onPlayerClickListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onPlayerClickListener: ((Player) -> Unit)? = null

    fun setPlayerClickListener(listener: (Player) -> Unit) {
        onPlayerClickListener = listener
    }

}