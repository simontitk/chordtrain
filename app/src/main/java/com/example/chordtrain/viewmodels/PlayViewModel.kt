package com.example.chordtrain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chordtrain.audio.ChordSequenceGenerator
import com.example.chordtrain.db.MusicalKey

class PlayViewModel(
    private val selectedLength: Int,
    private val selectedDifficulty: String,
    private val selectedMusicalKey: MusicalKey
): ViewModel() {

    private val generator = ChordSequenceGenerator(selectedMusicalKey, selectedDifficulty, selectedLength)
    val hasAnswered = MutableLiveData(false)
    var trueChordSequence = generator.getChordSequence()

    fun checkAnswer() {

        hasAnswered.value = true
        // TODO: checking logic
        // TODO: save record to database
    }

    fun skipSequence() {
        // TODO: save record to database
        generateNextSequence()
    }

    fun generateNextSequence() {
        hasAnswered.value = false
        trueChordSequence = generator.getChordSequence()
    }

}
