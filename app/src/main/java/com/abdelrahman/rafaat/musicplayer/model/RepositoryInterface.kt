package com.abdelrahman.rafaat.musicplayer.model

import retrofit2.Response

interface RepositoryInterface {

    fun getSongs(): List<MusicFile>
    fun getSongs(duration: Int): List<MusicFile>
    fun getArtists(): List<Artist>
    fun getAlbums(): List<Album>
    suspend fun getArtistImage(artistName: String): Response<DeezerResponse>

}