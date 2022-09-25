package com.abdelrahman.rafaat.musicplayer.model

import com.abdelrahman.rafaat.musicplayer.datasource.MusicClient

class Repository private constructor(
    private var musicClient: MusicClient
) : RepositoryInterface {

    override fun getSongs(): List<MusicFile> {
        return musicClient.getSongs()
    }

    override fun getSongs(duration: Int): List<MusicFile> {
        return emptyList()
    }

    override fun getArtists(): List<Artist> {
        return musicClient.getAllArtists()
    }

    override fun getAlbums(): List<Album> {
        return musicClient.getAllAlbums()
    }

    companion object {
        private var instance: Repository? = null
        fun getMusicClient(musicClient: MusicClient): Repository {
            if (instance == null)
                instance = Repository(musicClient)

            return instance!!
        }
    }


}