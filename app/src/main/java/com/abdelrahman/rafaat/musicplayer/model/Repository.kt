package com.abdelrahman.rafaat.musicplayer.model

import com.abdelrahman.rafaat.musicplayer.datasource.MusicClient
import com.abdelrahman.rafaat.musicplayer.network.RemoteSource
import retrofit2.Response

class Repository private constructor(
    private var musicClient: MusicClient, private var remoteSource: RemoteSource
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

    override suspend fun getArtistImage(artistName: String): Response<DeezerResponse> {
        return remoteSource.getArtistImage(artistName)
    }

    companion object {
        private var instance: Repository? = null
        fun getMusicClient(musicClient: MusicClient, remoteSource: RemoteSource): Repository {
            if (instance == null)
                instance = Repository(musicClient, remoteSource)

            return instance!!
        }
    }


}