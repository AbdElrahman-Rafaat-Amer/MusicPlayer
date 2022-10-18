package com.abdelrahman.rafaat.musicplayer.network

import com.abdelrahman.rafaat.musicplayer.model.DeezerResponse
import retrofit2.Call
import retrofit2.Response

interface RemoteSource {

    suspend fun getArtistImage(artistName: String): Response<DeezerResponse>

}