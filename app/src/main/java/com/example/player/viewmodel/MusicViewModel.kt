package com.example.player.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.player.data.MusicDao
import com.example.player.data.entity.MusicModel
import com.example.player.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val allMusicList = MutableLiveData<List<MusicModel>>()

    fun getAllMusic() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllMusicsFromInternal(allMusicList)
        }
    }

//    fun insert(musicModel: MusicModel) {
//        CoroutineScope(Dispatchers.IO).launch {
//            musicDao.insert(musicModel)
//        }
//    }


}