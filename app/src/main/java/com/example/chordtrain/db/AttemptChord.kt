package com.example.chordtrain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attempt_chord")
data class AttemptChord(
    @PrimaryKey(autoGenerate = true)
    val attemptChordId: Long = 0,
    val attemptId: Long,
    val chord: String,
    val hit: Boolean
)
