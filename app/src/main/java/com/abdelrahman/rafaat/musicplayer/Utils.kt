package com.abdelrahman.rafaat.musicplayer

import android.content.ContentUris
import android.net.Uri
import java.util.*

fun getAlbumArtUri(paramInt: Long): Uri {
    return ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"),
        paramInt
    )
}

fun getArtistUri(paramInt: Long): Uri {
    return ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/artistart"),
        paramInt
    )
}

fun isRTL(): Boolean {
    val language = Locale.getDefault().displayName
    val directionality = Character.getDirectionality(language[0]).toInt()
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
}