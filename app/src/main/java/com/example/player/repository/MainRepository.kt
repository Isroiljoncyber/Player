package com.example.player.repository

import androidx.lifecycle.LiveData
import com.example.player.data.entity.MusicModel
import javax.inject.Inject
import javax.inject.Singleton

interface MainRepository {

    suspend fun getAllMusicsFromInternal(): LiveData<MusicModel>

}