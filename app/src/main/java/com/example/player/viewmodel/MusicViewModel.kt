package com.example.player.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.player.App.Companion.lastMusicPosition
import com.example.player.R
import com.example.player.data.entity.MusicModel
import com.example.player.repository.MainRepository
import com.example.player.util.CreateNotification
import com.example.player.util.Repeat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.security.AccessController.getContext
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class MusicViewModel @Inject constructor(
    val repository: MainRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var mediaPlayer: MediaPlayer? = null

    private val PREFERENCES_FILE_NAME = "com.example.player.data"
    private val PREF_KEY = "uri"
    private val PREF_KEY_POSITION = "position"
    private val PREF_REPEAT = "repeat"

    // Observable variables which are connected to UI
    var tvTitle: ObservableField<String> = ObservableField()
    var tvArtist: ObservableField<String> = ObservableField()
    var tvTimeStart: ObservableField<String> = ObservableField()
    var tvTimeEnd: ObservableField<String> = ObservableField()
    var isPlaying: ObservableBoolean = ObservableBoolean()

    private val prefs = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private var rangePosition = 0;

    @Singleton
    fun getAllMusic() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllMusicsFromInternal()
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun setMusic(context: Context, position: Int, isPlay: Boolean, lastDurationTime: Int = 0) {
        try {
            repository.allMusicList[position].apply {
                val uri = Uri.parse(this.url_music)

                if (mediaPlayer != null) {
                    mediaPlayer!!.stop()
                    mediaPlayer = MediaPlayer.create(context, uri)
                } else {
                    mediaPlayer = MediaPlayer.create(context, uri)
                }

                // from another oart of the fragment we only set the music without playing it
                if (isPlay) {
                    mediaPlayer!!.seekTo(0)
                    mediaPlayer!!.start()
                    isPlaying.set(true)
                }
                if (lastDurationTime != 0) mediaPlayer!!.seekTo(lastDurationTime)

                setViews(position)
                rangePosition = position

                this.lottieVisible = true

                // Set the title and name of the music here
                // I am using dataBinding here first of all we have to declare it in the gradle.build

                // change the appearance of the notification
                changeNotification(position)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun changeNotification(position: Int) {
        try {
            if (mediaPlayer != null) if (mediaPlayer!!.isPlaying()) {
                CreateNotification.createNotification(
                    context,
                    repository.allMusicList[position],
                    R.drawable.ic_round_pause_24_black
                )
            } else {
                CreateNotification.createNotification(
                    context,
                    repository.allMusicList[position],
                    R.drawable.ic_play_24_black
                )
            }
        } catch (ex: java.lang.Exception) {
        }
    }

    fun playPausePlayList() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    isPlaying.set(false)
                    it.pause()
                } else {
                    it.start()
                    isPlaying.set(true)
                }
            }
            changeNotification(rangePosition)
        } catch (ex: Exception) {

        }
    }

    fun previousPlayList(context: Context) {
        try {
            mediaPlayer?.let {
                it.stop()
                lastMusicPosition = rangePosition
                rangePosition--
                if (rangePosition == -1) {
                    setMusic(context, repository.allMusicList.size - 1, true)
                } else if (repository.allMusicList.size != 0 && rangePosition < repository.allMusicList.size) {
                    setMusic(context, rangePosition, true)
                }
            }
        } catch (ex: Exception) {

        }
    }

    fun nextPlaylist(context: Context) {
        try {
            mediaPlayer?.let {
                it.stop()
                lastMusicPosition = rangePosition
                rangePosition++
                if (repository.allMusicList.size != 0 && rangePosition < repository.allMusicList.size) {
                    setMusic(context, rangePosition, true)
                } else if (rangePosition == repository.allMusicList.size) {
                    setMusic(context, 0, true)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    // Set views through the UI
    private fun setViews(position: Int) {
        repository.allMusicList[position].apply {
            tvTitle.set(this.title)
            tvArtist.set(this.artist)
        }
    }

    // Get and Set the last music model to sharedPreference
    fun setList() {
        setLastDuration()
        val gson = Gson()
        val json: String = gson.toJson(repository.allMusicList[rangePosition])
        set(PREF_KEY, json)
    }

    fun setLastDuration() {
        editor.putString(PREF_KEY_POSITION, mediaPlayer!!.currentPosition.toString()).commit()
    }

    fun getLastDuration(): Int {
        return Integer.parseInt(prefs.getString(PREF_KEY_POSITION, "0"))
    }

    fun set(key: String?, value: String?) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getLastMusic(): MusicModel? {
        val serializedObject: String? = prefs.getString(PREF_KEY, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<MusicModel?>() {}.type
            return gson.fromJson<MusicModel>(serializedObject, type)
        }
        return null
    }

    fun setRepeatSituation(repeat: Repeat) {
        editor.putString(PREF_REPEAT, repeat.toString())
        editor.commit()
    }

    fun getRepeatSituation(): String? {
        return prefs.getString(PREF_REPEAT, Repeat.REPEAT_DISABLE.toString())
    }
}