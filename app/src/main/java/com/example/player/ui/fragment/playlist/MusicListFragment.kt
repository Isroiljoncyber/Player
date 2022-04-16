package com.example.player.ui.fragment.playlist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.player.Service.OnClearActionService
import com.example.player.databinding.FragmentMusicListBinding
import com.example.player.ui.adapter.MusicAdapter
import com.example.player.ui.adapter.MusicPlayerCallBack
import com.example.player.util.CreateNotification
import com.example.player.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    private var adapterMusic: MusicAdapter? = null

    lateinit var viewModel: MusicViewModel

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
        viewModel = ViewModelProvider(requireActivity()).get(MusicViewModel::class.java)

        adapterMusic =
            MusicAdapter(viewModel.repository.allMusicList, object : MusicPlayerCallBack {
                override fun onClick(position: Int) {
                    context?.let { viewModel.setMusic(it, position, true) }
                }
            })

        binding.viewmodel = viewModel
        binding.recyclerMusic.adapter = adapterMusic

        binding.bottomBarBtnNext.setOnClickListener {
            context?.let { it1 -> viewModel.nextPlaylist(it1) }
        }

        binding.bottomBarBtnPrevious.setOnClickListener {
            context?.let { context -> viewModel.previousPlayList(context) }
        }

        binding.bottomBarBtnPlay.setOnClickListener {
            context?.let { it1 -> viewModel.playPausePlayList() }
        }

    }


    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CreateNotification.CHANNAL_ID,
                "MusicPlayer",
                NotificationManager.IMPORTANCE_LOW
            )
            context?.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }


    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            try {
                val action = intent.extras!!.getString("action_name")
                when (action) {
//                    CreateNotif.PREVIUS -> {
//                        try {
////                            ChangeMusic(false);
//                            Setname()
//                            setView()
//                        } catch (ex: Exception) {
//                        }
//                    }
//                    CreateNotif.NEXT -> {
//                        try {
//                            Setname()
//                            setView()
//                            //                            ChangeMusic(true);
//                        } catch (ex: Exception) {
//                        }
//                    }
//                    CreateNotif.PLAY -> {
//                        try {
//                            setView()
//                            //                            Play(false);
//                        } catch (ex: Exception) {
//                        }
//                    }
                }
            } catch (ex: Exception) {
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}