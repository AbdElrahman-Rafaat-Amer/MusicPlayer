package com.abdelrahman.rafaat.musicplayer.fragments.song

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelrahman.rafaat.musicplayer.R
import com.abdelrahman.rafaat.musicplayer.databinding.FragmentAllFilesBinding
import com.abdelrahman.rafaat.musicplayer.fragments.OnItemListener
import com.abdelrahman.rafaat.musicplayer.viewmodel.MusicViewModel


const val TAG = "AllFilesFragment"

class AllFilesFragment : Fragment(), OnItemListener {

    private lateinit var binding: FragmentAllFilesBinding
    private val adapter = MusicRecyclerAdapter(this)
    private val musicViewModel: MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFilesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: AllFilesFragment---------------------------------------")
        initRecyclerView()
        observeViewModel()

    }

    private fun initRecyclerView() {
        binding.musicFilesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.musicFilesRecyclerview.adapter = adapter
        val resId: Int = R.anim.lat
        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), resId)
        binding.musicFilesRecyclerview.layoutAnimation = animation
    }

    private fun observeViewModel() {
        musicViewModel.musicFiles.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.noFilesAnimation.visibility = View.VISIBLE
                binding.musicFilesRecyclerview.visibility = View.INVISIBLE
            } else {
                adapter.setList(it)
                binding.musicFilesRecyclerview.visibility = View.VISIBLE
                binding.noFilesAnimation.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClickListener(position: Int) {
        val bundle = Bundle()
        bundle.putInt("POSITION", position)
        Navigation.findNavController(requireView()).navigate(R.id.musicPlayer_Fragment, bundle)
    }
}