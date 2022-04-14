package com.example.player.ui.fragment.mainplay

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.player.R
import com.example.player.databinding.FragmentMainPlayBinding
import com.example.player.viewmodel.MusicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Array
import kotlin.Boolean
import kotlin.Exception
import kotlin.Int
import kotlin.IntArray
import kotlin.String
import kotlin.arrayOf
import kotlin.let

class MainPlayFragment : Fragment() {

    private var _binding: FragmentMainPlayBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusicViewModel
    private var totalTime: Int = 0

    private val PERMISSION_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        initBinding()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.isExistes
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)
        binding.viewmodel = viewModel
    }

    private fun initBinding() {

        if (viewModel.mediaPlayer != null) {
            setTotalTime()
        } else
            setDefaultMusic()

        binding.btnMenu.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainPlayFragment_to_musicList)
        }

        binding.btnNext.setOnClickListener {
            context?.let { it1 -> viewModel.nextPlaylist(it1) }
            setTotalTime()
        }

        binding.btnPrevious.setOnClickListener {
            context?.let { context -> viewModel.previousPlayList(context) }
            setTotalTime()
        }

        binding.btnPlay.setOnClickListener {
            context?.let { it1 -> viewModel.playpausePlayList(it1) }
        }

        // Refreshing seekbar
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
        CoroutineScope(Dispatchers.IO).launch {
            while (viewModel.mediaPlayer != null) {
                try {
                    val message = Message()
                    message.what = viewModel.mediaPlayer!!.currentPosition
                    handler.sendMessage(message)
                    delay(500)
                    if (viewModel.mediaPlayer!!.currentPosition == totalTime) {
                        viewModel.mediaPlayer!!.pause()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setDefaultMusic() {
        try {
            val lastMusic = viewModel.getLastMusic()
            viewModel.let { it1 ->
                if (lastMusic == null) {
                    // It sets the first music from all music list because it has not last music
                    context?.let { it1.setMusic(it, 0, false) }
                    setTotalTime()
                } else {
                    // If music player is not null then that is playing now and we only need to set view and last position of the last music
                    if (viewModel.mediaPlayer == null) {
                        val position = viewModel.repository.allMusicList.indexOf(lastMusic)
                        if (position != -1) {
                            context?.let { viewModel.setMusic(it, position, false) }
                        } else {
                            context?.let { viewModel.setMusic(it, 0, false) }
                        }
                        setTotalTime()
                    } else {
                        setTotalTime(lastMusic.later_time_position)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun setTotalTime(lastTime: Int = 0) {
        if (!lastTime.equals(0)) {
            totalTime = lastTime
        } else
            totalTime = viewModel.mediaPlayer!!.duration
        binding.seekMusic.max = totalTime
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