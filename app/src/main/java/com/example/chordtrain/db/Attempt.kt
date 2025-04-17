package com.example.chordtrain.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attempt")
data class Attempt(
    @PrimaryKey(autoGenerate = true)
    val attemptId: Long = 0,
    val difficulty: String,
    val key: String,
    val length: Int
)

