package com.abdelrahman.rafaat.musicplayer.fragments.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.size.ViewSizeResolver
import coil.transform.CircleCropTransformation
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.ArtistCustomRowBinding
import com.abdelrahman.rafaat.musicplayer.fragments.OnItemListener
import com.abdelrahman.rafaat.musicplayer.model.Artist
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ArtistRecyclerAdapter(private var onItemListener: OnItemListener) :
    ListAdapter<Artist, ArtistRecyclerAdapter.ViewHolder>(ArtistDiffCallback()) {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ArtistCustomRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArtist = getItem(position)
        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }
        holder.bind(currentArtist, context)
    }


    fun setList(artists: List<Artist>) {
        this.submitList(artists)
    }

    class ViewHolder(private var binding: ArtistCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist, context: Context) {
            binding.artistNameTextView.isSelected = true
            binding.artistNameTextView.text = artist.artistName
            binding.songsNumberTextView.text = artist.numberOfTracks
            binding.artistImageView.load(artist.imageUri) {
                crossfade(true)
                placeholder(R.drawable.ic_artist)
                error(R.drawable.ic_artist)
                transformations(CircleCropTransformation())
            }

        }

    }

    class ArtistDiffCallback : DiffUtil.ItemCallback<Artist>() {
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem.artistID == newItem.artistID
        }

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
            return oldItem == newItem
        }

    }
}