package com.example.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.player.databinding.FragmentMainPlayBinding
import com.example.player.databinding.FragmentMusicListBinding
import com.example.player.ui.Adapter.MusicAdapter

class MusicListFragment : Fragment() {

    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    private var adapterMusic: MusicAdapter? = null

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

//        adapterMusic = MusicAdapter()
        binding.recyclerMusic.adapter = adapterMusic

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