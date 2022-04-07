package com.example.player.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.example.player.data.MusicDao
import com.example.player.data.entity.MusicModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class RepositoryImpl @Inject constructor(
    private val musicDao: MusicDao,
    @ApplicationContext private val context: Context
) : MainRepository {

    suspend fun insert(musicModel: MusicModel) {
        withContext(Dispatchers.IO) {
            musicDao.insert(musicModel)
        }
    }

    override suspend fun getAllMusicsFromInternal(allMusic: MutableLiveData<List<MusicModel>>) {
        val _allMusic: MutableList<MusicModel> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val _musicName = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val _artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val _fullUri = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            do {
                val musicName: String = cursor.getString(_musicName)
                val artist: String = cursor.getString(_artist)
                val fullUri: String = cursor.getString(_fullUri)
                _allMusic.add(MusicModel(title = musicName, artist = artist, url_music = fullUri))
            } while (cursor.moveToNext())
            allMusic.postValue(_allMusic)
        }
    }

}