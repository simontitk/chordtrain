package com.example.chordtrain.db
import androidx.room.Embedded
import androidx.room.Relation

data class AttemptWithChords(
    @Embedded val attempt: Attempt,
    @Relation(
        parentColumn = "attemptId",
        entityColumn = "attemptId"
    )
    val chords: List<AttemptChord>
)
