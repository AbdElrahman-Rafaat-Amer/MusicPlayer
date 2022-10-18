package com.abdelrahman.rafaat.musicplayer.network

import android.util.Log
import com.abdelrahman.rafaat.musicplayer.model.DeezerResponse
import retrofit2.Call
import retrofit2.Response


private const val TAG = "MusicClient"

class MusicOnlineClient private constructor() : RemoteSource {

    private val quizHelper = MusicHelper.getClient


    companion object {
        private var instance: MusicOnlineClient? = null
        fun getMusicClient(): MusicOnlineClient {
            if (instance == null)
                instance = MusicOnlineClient()
            return instance!!
        }
    }

    override suspend fun getArtistImage(artistName: String): Response<DeezerResponse> {
        val response = quizHelper.getArtistImage(
            artistName = artistName
        )
        Log.i(TAG, "getQuestions: code----------> ${response.code()}")
        Log.i(TAG, "getQuestions: body----------> ${response.body()}")
        return response
    }


}