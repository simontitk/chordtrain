package com.example.chordtrain

data class AttemptDisplayData(
    val attemptId: Int,
    val key: String,
    val difficulty: String,
    val length: Int,
    val correctCount: Int,
    val totalChords: Int
)
