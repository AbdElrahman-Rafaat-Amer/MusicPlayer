package com.abdelrahman.rafaat.musicplayer.activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.ActivityMainBinding
import com.abdelrahman.rafaat.musicplayer.fragments.song.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.songs -> {
                    Log.i(TAG, "onCreate: bottomNavigation.setOnItemSelectedListener go to songs")
                    Navigation.findNavController(binding.navHostFragment)
                        .navigate(R.id.allFiles_fragment)
                }
                R.id.artists -> {
                    Log.i(TAG, "onCreate: bottomNavigation.setOnItemSelectedListener go to artists")
                    Navigation.findNavController(binding.navHostFragment)
                        .navigate(R.id.allArtists_fragment)
                }
                R.id.albums -> {
                    Log.i(TAG, "onCreate: bottomNavigation.setOnItemSelectedListener go to albums")
                    Navigation.findNavController(binding.navHostFragment)
                        .navigate(R.id.allAlbums_fragment)
                }
            }
            true
        }

        binding.bottomNavigationView.setOnItemReselectedListener {
            Log.i("MusicViewModel", "onCreate: --------------------")
        }


    }


}