package com.esaudev.gamesgarden.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.gamesgarden.databinding.ItemFeatureBinding
import com.esaudev.gamesgarden.ui.recycler_view_animations.Feature

class FeaturesAdapter: ListAdapter<Feature, FeaturesAdapter.FeatureViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Feature>() {
        override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean {
            return oldItem == newItem
        }
    }

    inner class FeatureViewHolder(val binding: ItemFeatureBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        return FeatureViewHolder(
            ItemFeatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {

        val newPosition = position % currentList.size
        val feature = currentList[newPosition]

        holder.binding.tvName.text = feature.name
        holder.binding.tvQuantity.text = feature.quantity.toString()

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(feature)
                }
            }
        }
    }

    protected var onItemClickListener : ((Feature) -> Unit)? = null

    fun setItemClickListener(listener: (Feature) -> Unit){
        onItemClickListener = listener
    }
}