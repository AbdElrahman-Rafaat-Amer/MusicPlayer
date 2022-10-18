package com.abdelrahman.rafaat.musicplayer.fragments.song

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.MusicCustomRowBinding
import com.abdelrahman.rafaat.musicplayer.fragments.OnItemListener
import com.abdelrahman.rafaat.musicplayer.getAlbumArtUri
import com.abdelrahman.rafaat.musicplayer.model.MusicFile
import com.bumptech.glide.Glide

class MusicRecyclerAdapter(private var onItemListener: OnItemListener) :
    ListAdapter<MusicFile, MusicRecyclerAdapter.ViewHolder>(MusicFileDiffCallback()) {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            MusicCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFile = getItem(position)
        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }
        holder.bind(currentFile, context)
    }

    fun setList(musicFiles: List<MusicFile>) {
        this.submitList(musicFiles)
    }

    class ViewHolder(private var binding: MusicCustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(musicFiles: MusicFile, context: Context) {
            binding.fileNameTextView.isSelected = true
            binding.fileNameTextView.text = musicFiles.title
            binding.artistNameTextView.text = musicFiles.artist
            Glide.with(context).load(getAlbumArtUri(musicFiles.albumID!!.toLong()).toString())
                .placeholder(R.drawable.ic_song).into(binding.fileImageView)
        }
    }

    class MusicFileDiffCallback : DiffUtil.ItemCallback<MusicFile>() {
        override fun areItemsTheSame(oldItem: MusicFile, newItem: MusicFile): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(oldItem: MusicFile, newItem: MusicFile): Boolean {
            return oldItem == newItem
        }

    }

}