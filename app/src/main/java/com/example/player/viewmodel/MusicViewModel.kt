package com.example.player.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.player.data.entity.MusicModel
import com.example.player.repository.MainRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class MusicViewModel @Inject constructor(
    val repository: MainRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var mediaPlayer: MediaPlayer? = null

    val PREFERENCES_FILE_NAME = "com.example.player.data"
    val PREF_KEY = "uri"

    // Observable variables which are connected to UI
    var tvTitle: ObservableField<String> = ObservableField()
    var tvArtist: ObservableField<String> = ObservableField()
    var tvTimeStart: ObservableField<String> = ObservableField()
    var tvTimeEnd: ObservableField<String> = ObservableField()

    val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    @Singleton
    fun getAllMusic() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllMusicsFromInternal()
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun playMusic(context: Context, position: Int) {
        repository.allMusicList[position].apply {
            val uri = Uri.parse(this.url_music)
            if (mediaPlayer != null) {
                mediaPlayer!!.stop()
                mediaPlayer = MediaPlayer.create(context, uri)
            } else {
                mediaPlayer = MediaPlayer.create(context, uri)
            }
            mediaPlayer!!.start()
            setViews(position)
            // Set the title and name of the music here
            // I am using databinding here first of all we have to declare it in the gradle.build
            setList(PREF_KEY, repository.allMusicList[position])
        }
    }

    fun setViews(position: Int) {
        repository.allMusicList[position].apply {
            tvTitle.set(this.title)
            tvArtist.set(this.artist)
        }
    }

//    fun insert(musicModel: MusicModel) {
//        CoroutineScope(Dispatchers.IO).launch {
//            musicDao.insert(musicModel)
//        }
//    }

    private val editor: SharedPreferences.Editor = prefs.edit()

    fun <T> setList(key: String?, list: T?) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getLastMusic(): MusicModel? {
        val arrayItems: MusicModel
        val serializedObject: String? = prefs.getString(PREF_KEY, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<MusicModel?>() {}.type
            return gson.fromJson<MusicModel>(serializedObject, type)
        }
        return null
    }


}