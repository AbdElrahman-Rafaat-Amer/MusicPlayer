package com.abdelrahman.rafaat.musicplayer.fragments


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.getAlbumArtUri
import com.abdelrahman.rafaat.musicplayer.model.Album
import com.bumptech.glide.Glide


class AlbumRecyclerAdapter(private var onItemListener: OnItemListener) :
    RecyclerView.Adapter<AlbumRecyclerAdapter.ViewHolder>() {

    private var albums: List<Album> = emptyList()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.album_custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentFile = albums[position]
        holder.albumNameTextView.isSelected = true
        holder.albumNameTextView.text = currentFile.albumName
        holder.artistNameTextView.text = currentFile.numberOfSongs

        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }

        Glide.with(context).load(getAlbumArtUri(currentFile.albumID!!.toLong()).toString())
            .placeholder(R.drawable.ic_album).into(holder.albumImageView)
    }


    override fun getItemCount(): Int {
        return albums.size
    }

    fun setList(albums: List<Album>) {
        this.albums = albums
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumNameTextView: TextView = itemView.findViewById(R.id.albumName_textView)
        var artistNameTextView: TextView = itemView.findViewById(R.id.artistName_textView)
        var albumImageView: ImageView = itemView.findViewById(R.id.album_imageView)
    }

}