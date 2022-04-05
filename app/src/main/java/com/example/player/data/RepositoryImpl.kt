package com.example.player.data

import com.example.player.data.entity.MusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val musicDao: MusicDao) {

    suspend fun insert(musicModel: MusicModel) {
        withContext(Dispatchers.IO) {
            musicDao.insert(musicModel)
        }
    }

}