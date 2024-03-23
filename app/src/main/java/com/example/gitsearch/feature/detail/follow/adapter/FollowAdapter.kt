package com.example.gitsearch.feature.detail.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitsearch.databinding.ItemFollowBinding
import com.example.gitsearch.feature.detail.follow.model.FollowModel

class FollowAdapter : ListAdapter<FollowModel, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFollowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(
            item,
        )

    }

    class MyViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FollowModel) {
            binding.apply {
                tvName.text = item.username
                Glide.with(itemView.context)
                    .load(item.avatar)
                    .into(ivAvatar)

            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowModel>() {
            override fun areItemsTheSame(oldItem: FollowModel, newItem: FollowModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FollowModel, newItem: FollowModel): Boolean {
                return oldItem.username == newItem.username
            }
        }
    }


}