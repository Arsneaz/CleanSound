package com.example.cleansound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleansound.databinding.ItemPlaylistBinding
import com.example.cleansound.model.playlists.ItemsItem

class FeaturedPlaylistsAdapter(private val onPlaylistClicked: (String) -> Unit) : ListAdapter<ItemsItem, FeaturedPlaylistsAdapter.FeaturedPlaylistsViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<ItemsItem>() {
        override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem == newItem
        }

    }

    class FeaturedPlaylistsViewHolder(private val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemsItem: ItemsItem) {
            binding.tvPlaylistName.text = itemsItem.name
            binding.tvPlaylistType.text = itemsItem.type
            itemsItem.images?.firstOrNull()?.url.let {imgUrl ->
                Glide.with(binding.root.context).load(imgUrl).into(binding.ivPlaylistImage)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeaturedPlaylistsViewHolder {
        val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context))
        return FeaturedPlaylistsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FeaturedPlaylistsViewHolder,
        position: Int
    ) {
        getItem(position)?.let {featuredPlaylist ->
            holder.itemView.setOnClickListener{
                onPlaylistClicked(featuredPlaylist.id!!)
            }
            holder.bind(featuredPlaylist)
        }
    }
}