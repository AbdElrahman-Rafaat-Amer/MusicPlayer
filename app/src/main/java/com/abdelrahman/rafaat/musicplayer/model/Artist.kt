package com.abdelrahman.rafaat.musicplayer.model

import java.io.Serializable

data class Artist(
    var artistID: String?,
    var artistName: String?,
    var numberOfAlbums: String?,
    var numberOfTracks: String?,
) : Serializable



