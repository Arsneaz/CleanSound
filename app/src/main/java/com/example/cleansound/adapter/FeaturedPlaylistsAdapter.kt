package com.example.cleansound.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cleansound.databinding.ItemPlaylistBinding
import com.example.cleansound.model.response.playlists.ItemsItem

class FeaturedPlaylistsAdapter(private val onPlaylistClicked: (String) -> Unit) : ListAdapter<com.example.cleansound.model.response.playlists.ItemsItem, FeaturedPlaylistsAdapter.FeaturedPlaylistsViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<com.example.cleansound.model.response.playlists.ItemsItem>() {
        override fun areItemsTheSame(oldItem: com.example.cleansound.model.response.playlists.ItemsItem, newItem: com.example.cleansound.model.response.playlists.ItemsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.example.cleansound.model.response.playlists.ItemsItem, newItem: com.example.cleansound.model.response.playlists.ItemsItem): Boolean {
            return oldItem == newItem
        }

    }

    class FeaturedPlaylistsViewHolder(private val binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemsItem: com.example.cleansound.model.response.playlists.ItemsItem) {

            itemsItem.images?.firstOrNull()?.url.let {imgUrl ->
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(imgUrl)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val palette = Palette.from(resource).generate()

                            val topColor = palette.getDominantColor(0)
                            val middleColor = palette.getDarkMutedColor(0)
                            val bottomColor = palette.getDarkVibrantColor(0)

                            val gradientDrawable = GradientDrawable(
                                GradientDrawable.Orientation.TOP_BOTTOM,
                                intArrayOf(topColor,middleColor,bottomColor)
                            )
                            gradientDrawable.cornerRadius = 20f

                            binding.bxImg.background = gradientDrawable

                            Glide.with(binding.root.context).load(imgUrl).into(binding.ivPlaylistImage)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            TODO("Not yet implemented")
                        }
                    })
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