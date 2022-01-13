package com.esaudev.gamesgarden.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esaudev.gamesgarden.databinding.ItemFeatureBinding

class FeaturesAdapter: ListAdapter<String, FeaturesAdapter.FeatureViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        val feature = currentList[position]

        holder.binding.tvFeature.text = feature
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(feature)
                }
            }
        }
    }

    protected var onItemClickListener : ((String) -> Unit)? = null

    fun setItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }
}