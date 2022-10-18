package com.abdelrahman.rafaat.musicplayer.fragments.album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.AlbumCustomRowBinding
import com.abdelrahman.rafaat.musicplayer.fragments.OnItemListener
import com.abdelrahman.rafaat.musicplayer.getAlbumArtUri
import com.abdelrahman.rafaat.musicplayer.model.Album
import com.bumptech.glide.Glide

class AlbumRecyclerAdapter(private var onItemListener: OnItemListener) :
    ListAdapter<Album, AlbumRecyclerAdapter.ViewHolder>(AlbumDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            AlbumCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAlbum = getItem(position)
        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }
        holder.bind(currentAlbum, context)
    }


    fun setList(albums: List<Album>) {
        this.submitList(albums)
    }

    class ViewHolder(private var binding: AlbumCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, context: Context) {
            binding.albumNameTextView.isSelected = true
            binding.albumNameTextView.text = album.albumName
            binding.artistNameTextView.text = album.numberOfSongs

            Glide.with(context).load(getAlbumArtUri(album.albumID!!.toLong()).toString())
                .placeholder(R.drawable.ic_album).into(binding.albumImageView)
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }

    }

}