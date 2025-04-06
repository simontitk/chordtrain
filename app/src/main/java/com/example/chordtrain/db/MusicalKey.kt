package com.example.chordtrain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musical_key")
data class MusicalKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val chord1: String,
    val chord2: String,
    val chord3: String,
    val chord4: String,
    val chord5: String,
    val chord6: String,
    val chord7: String
)