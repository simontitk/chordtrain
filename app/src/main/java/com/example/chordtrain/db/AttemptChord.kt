package com.example.chordtrain.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attempt_chord",
    foreignKeys = [ForeignKey(
        entity = Attempt::class,
        parentColumns = ["attemptId"],
        childColumns = ["attemptId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["attemptId"])]
)

data class AttemptChord(
    @PrimaryKey(autoGenerate = true)
    val attemptChordId: Long = 0,
    val attemptId: Long = 0,
    val chord: String,
    val hit: Boolean
)
