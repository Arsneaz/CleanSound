package com.example.cleansound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleansound.databinding.TrackItemBinding
import com.example.cleansound.model.Track

class TracksAdapter : PagingDataAdapter<com.example.cleansound.local.model.Track, TracksAdapter.TrackViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.cleansound.local.model.Track>() {
        override fun areItemsTheSame(
            oldItem: com.example.cleansound.local.model.Track,
            newItem: com.example.cleansound.local.model.Track
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: com.example.cleansound.local.model.Track,
            newItem: com.example.cleansound.local.model.Track
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    class TrackViewHolder(private val binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: com.example.cleansound.local.model.Track) {
            binding.trackTitle.text = track.name
            binding.trackArtist.text = track.artistNames
            Glide.with(binding.root.context).load(track.imageUrl).into(binding.trackImage)
        }
    }
}
