package com.example.cleansound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleansound.databinding.ItemTrackBinding
import com.example.cleansound.local.model.Track

class FeaturedTracksAdapter(private val onTrackClicked: (String) -> Unit) : PagingDataAdapter<Track, FeaturedTracksAdapter.TrackViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Track,  newItem: Track): Boolean {
            return oldItem.trackId == newItem.trackId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let {track ->
            holder.itemView.setOnClickListener{
                onTrackClicked(track.trackId)
            }
            holder.bind(track)
        }
    }

    class TrackViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.tvTrackTitle.text = track.name
            binding.tvTrackArtist.text = track.artistNames
            Glide.with(binding.root.context).load(track.imageUrl).into(binding.ivTrackImage)
        }
    }
}
