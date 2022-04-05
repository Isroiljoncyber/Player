package com.example.player.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.player.data.MusicDao
import com.example.player.data.entity.MusicModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val musicDao: MusicDao) : ViewModel() {

    fun insert(musicModel: MusicModel) {
        CoroutineScope(Dispatchers.IO).launch {
            musicDao.insert(musicModel)
        }
    }

}