package com.abdelrahman.rafaat.musicplayer.fragments.song

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.FragmentMusicPlayerBinding
import com.abdelrahman.rafaat.musicplayer.getAlbumArtUri
import com.abdelrahman.rafaat.musicplayer.isRTL
import com.abdelrahman.rafaat.musicplayer.model.MusicFile
import com.abdelrahman.rafaat.musicplayer.viewmodel.MusicViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import me.tankery.lib.circularseekbar.CircularSeekBar


class MusicPlayerFragment : Fragment() {

    private lateinit var binding: FragmentMusicPlayerBinding
    private val musicViewModel: MusicViewModel by activityViewModels()

    private var position: Int = 0
    private lateinit var currentMusicFile: MusicFile
    private lateinit var allFiles: List<MusicFile>
    private var total = 0
    private var current = 0
    private lateinit var glideContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicPlayerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        glideContext = requireContext()
        allFiles = musicViewModel.musicFiles.value!!
        position = arguments?.getInt("POSITION") ?: 0
        checkRTL()
        initUI()
        showFileDetails()
        startPlaying()
        setUpVisualizer()
    }

    private fun checkRTL() {
        if (isRTL()) {
            binding.previousFileButton.setBackgroundResource(R.drawable.ic_next_file)
            binding.nextFileButton.setBackgroundResource(R.drawable.ic_previous_file)
            binding.fastBackButton.setBackgroundResource(R.drawable.ic_fast_forward)
            binding.fastForwardButton.setBackgroundResource(R.drawable.ic_fast_back)
        }

    }


    private fun initUI() {
        binding.playButton.setOnClickListener {
            if (musicPlayer.isPlaying) {
                musicPlayer.pause()
                binding.playButton.setBackgroundResource(R.drawable.ic_play)
                updateSeekBar()

            } else {
                musicPlayer.start()
                binding.playButton.setBackgroundResource(R.drawable.ic_pause)
                updateSeekBar()

            }
        }

        binding.muteImageView.setOnClickListener {
            musicPlayer.setVolume(0.0F, 0.0F)
            binding.volumeSeekBar.progress = 0
        }

        binding.unMuteImageView.setOnClickListener {
            musicPlayer.setVolume(1.0F, 1.0F)
            binding.volumeSeekBar.progress = 100
        }

        binding.nextFileButton.setOnClickListener {
            position = (position + 1) % allFiles.size
            startPlaying()
            showFileDetails()
            setUpVisualizer()
        }

        binding.previousFileButton.setOnClickListener {
            position = if (position - 1 > -1) (position - 1) % allFiles.size else allFiles.size - 1
            startPlaying()
            showFileDetails()
            setUpVisualizer()
        }

        binding.fastForwardButton.setOnClickListener {
            if (musicPlayer.currentPosition + 10000 > musicPlayer.duration)
                musicPlayer.seekTo(musicPlayer.duration)
            else
                musicPlayer.seekTo(musicPlayer.currentPosition + 10000)
            binding.fileLengthSeekBar.progress = musicPlayer.currentPosition.toFloat()
        }

        binding.fastBackButton.setOnClickListener {
            musicPlayer.seekTo(musicPlayer.currentPosition - 10000)
            binding.fileLengthSeekBar.progress = musicPlayer.currentPosition.toFloat()
        }

        binding.volumeSeekBar.progress = 50
        binding.volumeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, isFromUser: Boolean) {
                val volume = seekBar.progress / 100f
                musicPlayer.setVolume(volume, volume)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        binding.fileLengthSeekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    musicPlayer.seekTo(progress.toInt())
                    circularSeekBar?.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
            }
        })
    }

    private fun showFileDetails() {
        currentMusicFile = allFiles[position]
        binding.fileInfoTextView.text = currentMusicFile.title.plus(" | " + currentMusicFile.artist)
        glideContext.let {
            Glide.with(it)
                .load(getAlbumArtUri(currentMusicFile.albumID!!.toLong()).toString())
                .placeholder(R.drawable.ic_song).into(binding.musicImageView)
        }


        val duration: String = millisecondsToString(currentMusicFile.duration!!.toInt())
        binding.fileLengthTextView.text = duration
        binding.fileLengthSeekBar.max = currentMusicFile.duration!!.toFloat()
        binding.fileLengthSeekBar.progress = 0F
    }

    private fun startPlaying() {
        musicPlayer.stop()
        musicPlayer.release()
        currentMusicFile = allFiles[position]
        musicPlayer = MediaPlayer()
        musicPlayer.setDataSource(currentMusicFile.data!!)
        musicPlayer.prepare()
        startAnimation()
        musicPlayer.setVolume(0.5f, 0.5f)
        binding.playButton.setBackgroundResource(R.drawable.ic_pause)
        total = musicPlayer.duration
        current = 0
        musicPlayer.setOnCompletionListener {
            position = (position + 1) % allFiles.size
            showFileDetails()
            startPlaying()
            setUpVisualizer()
        }
        updateSeekBar()
        musicPlayer.start()
    }

    private fun millisecondsToString(time: Int): String {
        var elapsedTime = ""
        val seconds = time / 1000 % 60
        val minutes = time / 1000 / 60 % 60
        val hours = time / 1000 / 60 / 60

        if (hours > 0)
            elapsedTime = "$hours:"

        if (minutes < 10)
            elapsedTime += "0"
        elapsedTime += "$minutes:"

        if (seconds < 10) {
            elapsedTime += "0"
        }
        elapsedTime += "$seconds"
        return elapsedTime
    }

    private fun startAnimation() {
        val objectAnimator = ObjectAnimator.ofFloat(binding.musicImageView, "rotation", 0f, 360f)
        objectAnimator.duration = 1000
        val animator = AnimatorSet()
        animator.playTogether(objectAnimator)
        animator.start()
    }

    private fun setUpVisualizer() {
        binding.visualizer.setDensity(90F)
        binding.visualizer.setPlayer(musicPlayer.audioSessionId)
        context?.getColor(R.color.green)?.let { binding.visualizer.setColor(it) }
    }

    companion object {
        private var musicPlayer: MediaPlayer = MediaPlayer()
    }

    private fun updateSeekBar() {
        CoroutineScope(Dispatchers.Main).launch {
            total = musicPlayer.duration
            while (current < total && musicPlayer.isPlaying) {
                try {
                    current = musicPlayer.currentPosition
                    val elapsedTime: String = millisecondsToString(current)
                    Handler(Looper.getMainLooper()).post {
                        binding.elapsedTimeTextView.text = elapsedTime
                        binding.fileLengthSeekBar.progress = current.toFloat()
                    }
                    delay(1000)
                } catch (exception: IllegalStateException) {
                    Log.i(TAG, "exception -----------------> ${exception.message}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.visualizer.release()
    }

}