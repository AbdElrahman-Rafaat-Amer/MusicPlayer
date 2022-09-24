package com.abdelrahman.rafaat.musicplayer.datasource

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.abdelrahman.rafaat.musicplayer.fragments.TAG
import com.abdelrahman.rafaat.musicplayer.model.MusicFile

class MusicClient private constructor(var context: Context) {
    private val musicFiles: ArrayList<MusicFile> = ArrayList()

    init {
        getAllSongs()
    }

    fun getSongs(duration: Int): List<MusicFile> {
        return emptyList()//musicFiles.filter { musicFile -> musicFile.duration!! >= duration }
    }

    fun getSongs(): List<MusicFile> {
        return musicFiles.toList()
    }


    private fun getAllSongs() {
        val contentResolver = context.contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songCursor = contentResolver.query(songUri, null, null, null, null)
        if (songCursor != null && songCursor.moveToFirst()) {

            val indexID = songCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val indexData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val indexName = songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
            val indexDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val indexAlbums = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val indexAlbumID = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)


            do {
                val id = songCursor.getString(indexID)
                val artist = songCursor.getString(indexArtist)
                val title = songCursor.getString(indexTitle)
                val data = songCursor.getString(indexData)
                val name = songCursor.getString(indexName)
                val duration = songCursor.getString(indexDuration)
                val album = songCursor.getString(indexAlbums)
                val albumID = songCursor.getString(indexAlbumID)
                musicFiles.add(
                    MusicFile(
                        id, artist, title,
                        data, name, duration, album, albumID
                    )
                )
            } while (songCursor.moveToNext())
        }
        songCursor?.close()
    }

    companion object {
        private var instance: MusicClient? = null
        fun getMusicClient(context: Context): MusicClient {
            if (instance == null)
                instance = MusicClient(context)
            return instance!!
        }
    }


}