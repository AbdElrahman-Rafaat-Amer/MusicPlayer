package com.abdelrahman.rafaat.musicplayer.model

interface RepositoryInterface {

    fun getSongs(): List<MusicFile>
    fun getSongs(duration: Int): List<MusicFile>
    fun getArtists(): List<Artist>
    fun getAlbums(): List<Album>

}