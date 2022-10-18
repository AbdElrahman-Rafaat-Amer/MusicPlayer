package com.abdelrahman.rafaat.musicplayer.network

import com.abdelrahman.rafaat.musicplayer.model.DeezerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicService {
    @GET("search/artist&limit=1")
    suspend fun getArtistImage(
        @Query("q") artistName: String
    ): Response<DeezerResponse>

}