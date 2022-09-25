package com.abdelrahman.rafaat.musicplayer.model

import java.io.Serializable

data class Album(
    var ID: String?,
    var artist: String?,
    var albumName: String?,
    var albumID: String?,
    var artistID: String?,
    var numberOfSongs: String?,
    var numberOfSongsArtist: String?,
    var firstYear: String?,
    var endYear: String?,
) : Serializable


