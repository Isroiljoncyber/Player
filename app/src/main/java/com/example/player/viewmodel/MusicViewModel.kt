package com.example.player.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.player.data.entity.MusicModel
import com.example.player.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private var mediaPlayer: MediaPlayer? = null
    var allMusicList = mutableListOf<MusicModel>()

    @Singleton
    fun getAllMusic() {
        CoroutineScope(Dispatchers.IO).launch {
            allMusicList = repository.getAllMusicsFromInternal()
        }
        print(allMusicList.size)
    }

    fun playMusic(context: Context, sUri: String) {

        val uri = Uri.parse(sUri)
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer = MediaPlayer.create(context, uri)
        } else {
            mediaPlayer = MediaPlayer.create(context, uri)
        }

        mediaPlayer!!.start()

    }

//    fun insert(musicModel: MusicModel) {
//        CoroutineScope(Dispatchers.IO).launch {
//            musicDao.insert(musicModel)
//        }
//    }

}