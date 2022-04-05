package com.example.player.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.player.data.entity.MusicModel

@Database(entities = [MusicModel::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    companion object {
        val DATABASE_NAME = "newPlayer.db"
    }
}