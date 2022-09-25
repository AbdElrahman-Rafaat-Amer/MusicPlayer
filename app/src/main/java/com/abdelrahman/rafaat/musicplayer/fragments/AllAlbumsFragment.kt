package com.abdelrahman.rafaat.musicplayer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.FragmentAllAlbumsBinding
import com.abdelrahman.rafaat.musicplayer.model.Album
import com.abdelrahman.rafaat.musicplayer.viewmodel.MusicViewModel


class AllAlbumsFragment : Fragment(), OnItemListener {

    private lateinit var binding: FragmentAllAlbumsBinding
    private val albumViewModel: MusicViewModel by activityViewModels()
    private val adapter = AlbumRecyclerAdapter(this)
    private lateinit var albums: List<Album>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAlbumsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: AllAlbumsFragment---------------------------------------")
        albumViewModel.getAlbums()
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        binding.albumsRecyclerview.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.albumsRecyclerview.adapter = adapter
        val resId: Int = R.anim.lat
        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), resId)
        binding.albumsRecyclerview.layoutAnimation = animation
    }

    private fun observeViewModel() {
        albumViewModel.albums.observe(viewLifecycleOwner) {
            albums = it
            if (it.isEmpty()) {
                binding.noFilesAnimation.visibility = View.VISIBLE
                binding.albumsRecyclerview.visibility = View.INVISIBLE
            } else {
                adapter.setList(it)
                binding.albumsRecyclerview.visibility = View.VISIBLE
                binding.noFilesAnimation.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClickListener(position: Int) {

    }
}