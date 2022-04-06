package com.example.player.repository

import androidx.lifecycle.LiveData
import com.example.player.data.MusicDao
import com.example.player.data.entity.MusicModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val musicDao: MusicDao) : MainRepository {

    suspend fun insert(musicModel: MusicModel) {
        withContext(Dispatchers.IO) {
            musicDao.insert(musicModel)
        }
    }

    override suspend fun getAllMusicsFromInternal(): LiveData<MusicModel> {
        TODO("Not yet implemented")
    }

}