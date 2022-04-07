package com.example.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.player.databinding.FragmentMainPlayBinding
import com.example.player.databinding.FragmentMusicListBinding
import com.example.player.ui.Adapter.MusicAdapter
import com.example.player.viewmodel.MusicViewModel

class MusicListFragment : Fragment() {

    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    private var adapterMusic: MusicAdapter? = null

    private val viewModel by viewModels<MusicViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMusicListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allMusicList.observe(viewLifecycleOwner) {
            adapterMusic = MusicAdapter(it)
            binding.recyclerMusic.adapter = adapterMusic
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MusicListFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}