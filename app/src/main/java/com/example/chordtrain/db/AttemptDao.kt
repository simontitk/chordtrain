package com.example.chordtrain.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface AttemptDao {

    @Insert
    suspend fun insertAttempt(attempt: Attempt): Long

    @Insert
    suspend fun insertAttemptChords(chords: List<AttemptChord>)

    @Transaction
    suspend fun insertAttemptWithChords(attempt: Attempt, attemptChords: List<AttemptChord>): Long {
        val attemptId = insertAttempt(attempt)
        val chordsWithAttemptId = attemptChords.map { it.copy(attemptId = attemptId) }
        insertAttemptChords(chordsWithAttemptId)
        return attemptId
    }
}