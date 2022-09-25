package com.abdelrahman.rafaat.musicplayer.fragments


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.getAlbumArtUri
import com.abdelrahman.rafaat.musicplayer.getArtistUri
import com.abdelrahman.rafaat.musicplayer.model.Artist
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


class ArtistRecyclerAdapter(private var onItemListener: OnItemListener) :
    RecyclerView.Adapter<ArtistRecyclerAdapter.ViewHolder>() {

    private var artists: List<Artist> = emptyList()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.artist_custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentFile = artists[position]
        holder.artistNameTextView.isSelected = true
        holder.artistNameTextView.text = currentFile.artistName
        holder.songsNumberTextView.text = currentFile.numberOfTracks

        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }

        Glide.with(context).load(getArtistUri(currentFile.artistID!!.toLong()).toString())
            .placeholder(R.drawable.ic_artist).into(holder.artistImageView)
    }


    override fun getItemCount(): Int {
        return artists.size
    }

    fun setList(artists: List<Artist>) {
        this.artists = artists
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var artistNameTextView: TextView = itemView.findViewById(R.id.artistName_textView)
        var songsNumberTextView: TextView = itemView.findViewById(R.id.songsNumber_textView)
        var artistImageView: CircleImageView = itemView.findViewById(R.id.artist_imageView)
    }

}