package com.example.player.ui.fragment.mainplay

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.player.R
import com.example.player.data.entity.MusicModel
import com.example.player.databinding.FragmentMainPlayBinding
import com.example.player.viewmodel.MusicViewModel

class MainPlayFragment : Fragment() {

    private var _binding: FragmentMainPlayBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusicViewModel
    private var totalTime: Int = 0

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
        viewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)
        binding.viewmodel = viewModel

        // set default music when user open the app first time
        setDefaultMusic()

        binding.btnMenu.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainPlayFragment_to_musicList)
        }

        binding.seekMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.mediaPlayer?.seekTo(progress)
                    binding.seekMusic.progress = progress
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        Thread {
            while (viewModel.mediaPlayer != null) {
                try {
                    val message = Message()
                    message.what = viewModel.mediaPlayer!!.currentPosition
                    handler.sendMessage(message)
//                    Thread.sleep(1000)
                    if (viewModel.mediaPlayer!!.currentPosition == totalTime) {
                        viewModel.mediaPlayer!!.pause()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

    }

    private fun setDefaultMusic() {
        try {
            val lastMusic = viewModel.getLastMusic()
            viewModel.apply {
                if (lastMusic == null) {
                    context?.let { this.playMusic(it, 0) }
                    this.mediaPlayer?.also {
                        it.isLooping = true
                        it.seekTo(0)
                        totalTime = it.duration
                        binding.seekMusic.max = totalTime
                    }
                } else {
                    val position = viewModel.repository.allMusicList.indexOf(lastMusic)
                    viewModel.setViews(position)
                    totalTime = viewModel.mediaPlayer!!.duration
                    binding.seekMusic.max = totalTime
                }
            }

        } catch (ex: Exception) {

        }
    }

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            binding.seekMusic.progress = currentPosition
            val time: String = createTime(currentPosition)
            viewModel.tvTimeStart.set(time)
            val end: String = createTime(totalTime - currentPosition)
            viewModel.tvTimeEnd.set("-$end")
        }
    }

    private fun createTime(currentPosition: Int): String {
        var timelable: String? = ""
        val min = currentPosition / 1000 / 60
        val millis = currentPosition / 1000 % 60
        timelable = "$min:"
        if (millis < 10) timelable += "0"
        timelable += millis
        return timelable
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}