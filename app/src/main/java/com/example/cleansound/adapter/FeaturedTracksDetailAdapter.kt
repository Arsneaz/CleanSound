package com.example.cleansound.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cleansound.databinding.ItemTrackBinding
import com.example.cleansound.model.tracks.Track

class FeaturedTracksDetailAdapter (private val onPlaylistClicked: (String) -> Unit) : ListAdapter<Track, FeaturedTracksDetailAdapter.FeaturedTracksDetailViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }

    }

    class FeaturedTracksDetailViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.tvTrackTitle.text = track.name
            binding.tvTrackArtist.text = track.artists?.joinToString { it?.name.orEmpty() }
            track.album?.images?.firstOrNull()?.url.let { imgUrl ->
                Glide.with(binding.root.context).load(imgUrl).into(binding.ivTrackImage)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeaturedTracksDetailViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context))
        return FeaturedTracksDetailViewHolder(binding)
    }



    override fun onBindViewHolder(
        holder: FeaturedTracksDetailViewHolder,
        position: Int
    ) {
        getItem(position)?.let { trackId ->
            holder.itemView.setOnClickListener {
                onPlaylistClicked(trackId.id!!)
            }
            holder.bind(trackId)
        }
    }
}
