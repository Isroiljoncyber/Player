package com.example.player.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.player.data.entity.MusicModel

@Dao
interface MusicDao {

    @Insert
    fun insert(musicModel: MusicModel)

}