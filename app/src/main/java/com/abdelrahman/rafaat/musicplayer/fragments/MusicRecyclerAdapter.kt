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
import com.abdelrahman.rafaat.musicplayer.model.MusicFile
import com.bumptech.glide.Glide


class MusicRecyclerAdapter(private var onItemListener: OnItemListener) :
    RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder>() {

    private var musicFiles: List<MusicFile> = emptyList()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentFile = musicFiles[position]
        holder.fileNameTextView.isSelected = true
        holder.fileNameTextView.text = currentFile.title
        holder.artistNameTextView.text = currentFile.artist
/*        holder.fileNameTextView2.text = currentFile.title
        holder.fileNameTextView3.text = currentFile.data
        holder.fileNameTextView4.text = currentFile.name
        holder.fileNameTextView5.text = currentFile.duration.toString()
        holder.fileNameTextView6.text = currentFile.album*/

        holder.itemView.setOnClickListener {
            onItemListener.onItemClickListener(position)
        }

        Glide.with(context).load(getAlbumArtUri(currentFile.albumID!!.toLong()).toString())
            .placeholder(R.drawable.ic_song).into(holder.imageView)
    }


    override fun getItemCount(): Int {
        return musicFiles.size
    }

    fun setList(musicFiles: List<MusicFile>) {
        this.musicFiles = musicFiles
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileNameTextView: TextView = itemView.findViewById(R.id.fileName_textView)
        var artistNameTextView: TextView = itemView.findViewById(R.id.artistName_textView)

        /*      var fileNameTextView2: TextView = itemView.findViewById(R.id.fileName_textView2)
              var fileNameTextView3: TextView = itemView.findViewById(R.id.fileName_textView3)
              var fileNameTextView4: TextView = itemView.findViewById(R.id.fileName_textView4)
              var fileNameTextView5: TextView = itemView.findViewById(R.id.fileName_textView5)
              var fileNameTextView6: TextView = itemView.findViewById(R.id.fileName_textView6)*/
        var imageView: ImageView = itemView.findViewById(R.id.file_imageView)
    }

}