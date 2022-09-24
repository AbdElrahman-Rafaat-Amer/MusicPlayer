package com.abdelrahman.rafaat.musicplayer.model

interface RepositoryInterface {

    fun getSongs(): List<MusicFile>
    fun getSongs(duration: Int): List<MusicFile>

}