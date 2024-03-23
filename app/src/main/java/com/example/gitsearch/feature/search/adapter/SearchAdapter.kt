package com.example.gitsearch.feature.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitsearch.databinding.ItemSearchBinding
import com.example.gitsearch.feature.search.model.SearchModel

class SearchAdapter : ListAdapter<SearchModel, SearchAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSearchBinding.inflate(
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
            onItemClickListener
        )

    }

    class MyViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchModel, onItemClickListener: OnItemClickListener) {
            binding.apply {
                tvUserName.text = item.login
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .into(ivProfileImage)

                root.setOnClickListener {
                    onItemClickListener.onItemClick(item.login)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchModel>() {
            override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

}