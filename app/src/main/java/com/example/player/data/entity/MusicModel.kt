package com.example.player.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "history")
data class MusicModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var artist: String,
    var url_music: String,
    var duration_time: String,
    var later_time_position: String,
    var url_image: String = ""
)