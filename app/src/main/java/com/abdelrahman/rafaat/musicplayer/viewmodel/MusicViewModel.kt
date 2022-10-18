package com.abdelrahman.rafaat.musicplayer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.abdelrahman.rafaat.musicplayer.ConnectionLiveData
import com.abdelrahman.rafaat.musicplayer.TAG
import com.abdelrahman.rafaat.musicplayer.datasource.MusicClient
import com.abdelrahman.rafaat.musicplayer.isOnline
import com.abdelrahman.rafaat.musicplayer.model.*
import com.abdelrahman.rafaat.musicplayer.network.MusicOnlineClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class MusicViewModel(application: Application) : AndroidViewModel(application) {

    private var _iRepo: RepositoryInterface
    private var _musicFiles = MutableLiveData<List<MusicFile>>()
    val musicFiles: LiveData<List<MusicFile>> = _musicFiles

    private var _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> = _artists

    private var _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    init {
        _iRepo =
            Repository.getMusicClient(
                MusicClient.getMusicClient(application.applicationContext),
                MusicOnlineClient.getMusicClient()
            )
        getSongs()
        Log.i(TAG, "init: ")
    }

    private fun getSongs() {
        viewModelScope.launch {
            val response = _iRepo.getSongs()
            withContext(Dispatchers.Main) {
                _musicFiles.postValue(response)
            }
        }
    }

    fun getAlbums() {
        viewModelScope.launch {
            val response = _iRepo.getAlbums()
            withContext(Dispatchers.Main) {
                _albums.postValue(response)
            }
        }
    }

    fun getArtists() {
        viewModelScope.launch {
            val response = _iRepo.getArtists()
            withContext(Dispatchers.Main) {
                _artists.postValue(response)
                if (isOnline(getApplication<Application>().applicationContext))
                    getArtistImage(response)
            }
        }
    }

    private fun getArtistImage(artists: List<Artist>) {
        viewModelScope.launch {
            val newArtists = ArrayList<Artist>()
            for (artist in artists) {
                newArtists.add(
                    Artist(
                        artist.artistID,
                        artist.artistName,
                        artist.numberOfAlbums,
                        artist.numberOfTracks,
                        artist.imageUri
                    )
                )
            }
            for (index in newArtists.indices) {
                val response = _iRepo.getArtistImage(newArtists[index].artistName!!)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.code() == 200 && newArtists[index].artistName != "<unknown>") {
                        val deezerResponse = response.body()
                        if (deezerResponse?.data?.size!! > 0) {
                            val imageUrl = getHighestQuality(deezerResponse.data[0])
                            val placeHolder = imageUrl.contains("/images/artist/")
                            if (placeHolder) {
                                newArtists[index].imageUri = imageUrl
                            }
                        }

                    }
                }
            }
            _artists.postValue(newArtists)
        }
    }

    private fun getHighestQuality(imageUrl: Data): String {
        return when {
            imageUrl.pictureXl.isNotEmpty() -> imageUrl.pictureXl
            imageUrl.pictureBig.isNotEmpty() -> imageUrl.pictureBig
            imageUrl.pictureMedium.isNotEmpty() -> imageUrl.pictureMedium
            imageUrl.pictureSmall.isNotEmpty() -> imageUrl.pictureSmall
            imageUrl.picture.isNotEmpty() -> imageUrl.picture
            else -> ""
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}