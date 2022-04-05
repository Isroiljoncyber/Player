package com.example.player.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.player.R
import com.example.player.databinding.FragmentMainPlayBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainPlayFragment : Fragment() {

    private var _binding: FragmentMainPlayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainPlayFragment_to_musicList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}