package com.abdelrahman.rafaat.musicplayer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.abdelrahman.rafaat.musicplayer.datasource.MusicClient
import com.abdelrahman.rafaat.musicplayer.fragments.TAG
import com.abdelrahman.rafaat.musicplayer.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            Repository.getMusicClient(MusicClient.getMusicClient(application.applicationContext))
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

    fun getArtists() {
        viewModelScope.launch {
            val response = _iRepo.getArtists()
            withContext(Dispatchers.Main) {
                _artists.postValue(response)
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


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}