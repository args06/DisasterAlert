package com.example.disasteralert.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.disasteralert.R
import com.example.disasteralert.data.remote.response.GeometriesItem
import com.example.disasteralert.databinding.DisasterDetailItemBinding

class DisasterAdapter : ListAdapter<GeometriesItem, DisasterAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DisasterDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disasterItem = getItem(position)
        holder.bind(disasterItem)
    }

    class ViewHolder(var binding: DisasterDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disasterItem: GeometriesItem) {
            binding.apply {
                Glide.with(itemView.context).load(disasterItem.properties.imageUrl).apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                ).into(ivDisasterImage)
                tvDisasterType.text = disasterItem.properties.disasterType
                tvDisasterTime.text = disasterItem.properties.createdAt
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GeometriesItem> =
            object : DiffUtil.ItemCallback<GeometriesItem>() {
                override fun areItemsTheSame(
                    oldItem: GeometriesItem, newItem: GeometriesItem
                ): Boolean {
                    return oldItem.type == newItem.type
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: GeometriesItem, newItem: GeometriesItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}