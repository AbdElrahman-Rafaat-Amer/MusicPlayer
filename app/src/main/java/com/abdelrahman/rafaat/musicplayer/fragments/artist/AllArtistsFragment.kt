package com.abdelrahman.rafaat.musicplayer.fragments.artist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.FragmentAllArtistsBinding
import com.abdelrahman.rafaat.musicplayer.fragments.OnItemListener
import com.abdelrahman.rafaat.musicplayer.model.Artist
import com.abdelrahman.rafaat.musicplayer.viewmodel.MusicViewModel


class AllArtistsFragment : Fragment(), OnItemListener {

    private val TAG = "AllArtistsFragment"
    private lateinit var binding: FragmentAllArtistsBinding
    private val artistViewModel: MusicViewModel by activityViewModels()
    private val adapter = ArtistRecyclerAdapter(this)
    private lateinit var artists: List<Artist>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllArtistsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: AllArtistsFragment---------------------------------------")
        artistViewModel.getArtists()
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        binding.artistsRecyclerview.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.artistsRecyclerview.adapter = adapter
        val resId: Int = R.anim.lat
        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), resId)
        binding.artistsRecyclerview.layoutAnimation = animation
    }

    private fun observeViewModel() {
        artistViewModel.artists.observe(viewLifecycleOwner) {
            artists = it
            if (it.isEmpty()) {
                binding.noFilesAnimation.visibility = View.VISIBLE
                binding.artistsRecyclerview.visibility = View.INVISIBLE
            } else {
                Log.i(TAG, "observeViewModel: $it")
                adapter.setList(it)
                binding.artistsRecyclerview.visibility = View.VISIBLE
                binding.noFilesAnimation.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClickListener(position: Int) {
        /* val bundle = Bundle()
         bundle.putInt("POSITION", position)
         Navigation.findNavController(requireView()).navigate(R.id.musicPlayer_Fragment, bundle)*/
        Log.i(TAG, "onItemClickListener: position-------> $position")
        val artist = artistViewModel.artists.value?.get(0)
        Log.i(TAG, "onItemClickListener: artist---------> $artist")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: -----------------------")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ------------------------")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: -------------------------------")
    }
}