package com.abdelrahman.rafaat.musicplayer.model

import java.io.Serializable

data class MusicFile(
    var ID: String?,
    var artist: String?,
    var title: String?,
    var data: String?,
    var name: String?,
    var duration: String?,
    var album: String?,
    var albumID: String?
) : Serializable
