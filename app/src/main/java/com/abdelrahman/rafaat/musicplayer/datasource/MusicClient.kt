package com.abdelrahman.rafaat.musicplayer.datasource

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.abdelrahman.rafaat.musicplayer.fragments.TAG
import com.abdelrahman.rafaat.musicplayer.model.Album
import com.abdelrahman.rafaat.musicplayer.model.Artist
import com.abdelrahman.rafaat.musicplayer.model.MusicFile

class MusicClient private constructor(var context: Context) {
    private val musicFiles: ArrayList<MusicFile> = ArrayList()

    init {
        getAllSongs()
    }

    fun getAllAlbums(): List<Album> {
        val albums: ArrayList<Album> = ArrayList()
        val contentResolver = context.contentResolver
        val albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val albumCursor = contentResolver.query(albumUri, null, null, null, null)

        if (albumCursor != null && albumCursor.moveToFirst()) {

            val indexID = albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val indexArtist = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)
            val indexAlbums = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)
            val indexAlbumID = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID)
            val indexArtistID = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST_ID)
            val indexNumberOfSongs =
                albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)
            val indexNumberOfSongsArtist =
                albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS_FOR_ARTIST)
            val indexFirstYear = albumCursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR)
            val indexEndYear = albumCursor.getColumnIndex(MediaStore.Audio.Albums.LAST_YEAR)

            do {
                val id = albumCursor.getString(indexID)
                val artist = albumCursor.getString(indexArtist)
                val album = albumCursor.getString(indexAlbums)
                val albumID = albumCursor.getString(indexAlbumID)
                val artistID = albumCursor.getString(indexArtistID)
                val numberOfSongs = albumCursor.getString(indexNumberOfSongs)
                val numberOfSongsArtist = albumCursor.getString(indexNumberOfSongsArtist)
                val firstYear = albumCursor.getString(indexFirstYear)
                val endYear = albumCursor.getString(indexEndYear)

                albums.add(
                    Album(
                        id, artist, album, albumID, artistID,
                        numberOfSongs, numberOfSongsArtist, firstYear, endYear
                    )
                )
            } while (albumCursor.moveToNext())
        }
        albumCursor?.close()
        Log.i(TAG, "getAllAlbums: size--------------------> ${albums.size}")
        return albums.toList()
    }

    fun getAllArtists(): List<Artist> {
        val artists: ArrayList<Artist> = ArrayList()
        val contentResolver = context.contentResolver
        val artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
        val artistCursor = contentResolver.query(artistUri, null, null, null, null)

        if (artistCursor != null && artistCursor.moveToFirst()) {

            val indexID = artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID)
            val indexArtist = artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)
            val indexNumberOfAlbums =
                artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
            val indexNumberOfTracks =
                artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)

            do {
                val id = artistCursor.getString(indexID)
                val artist = artistCursor.getString(indexArtist)
                val numberOfAlbums = artistCursor.getString(indexNumberOfAlbums)
                val numberOfTracks = artistCursor.getString(indexNumberOfTracks)
                artists.add(
                    Artist(id, artist, numberOfAlbums, numberOfTracks)
                )
            } while (artistCursor.moveToNext())
        }
        artistCursor?.close()
        Log.i(TAG, "getAllArtists: size--------------------> ${artists.size}")
        return artists.toList()
    }


    fun getSongs(duration: Int): List<MusicFile> {
        return musicFiles.filter { musicFile -> musicFile.duration!!.toInt() >= duration }
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