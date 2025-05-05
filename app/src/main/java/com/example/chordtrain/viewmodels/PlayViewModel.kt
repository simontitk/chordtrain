package com.example.chordtrain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chordtrain.audio.ChordSequenceGenerator
import com.example.chordtrain.db.Attempt
import com.example.chordtrain.db.AttemptChord
import com.example.chordtrain.db.MusicalKey

class PlayViewModel(
    private val selectedLength: Int,
    private val selectedDifficulty: String,
    private val selectedMusicalKey: MusicalKey
): ViewModel() {

    private val generator = ChordSequenceGenerator(selectedMusicalKey, selectedDifficulty, selectedLength)
    val hasAnswered = MutableLiveData(false)
    var trueChordSequence = generator.getRandomChordSequence()

    fun getPossibleChords(): List<String>{
        return generator.getPossibleChords()
    }

    fun checkAnswer(answerChordSequence: List<String>): Pair<Attempt, List<AttemptChord>> {
        val attempt = Attempt(
            difficulty=selectedDifficulty,
            key=selectedMusicalKey.name,
            length=selectedLength
        )
        val attemptChords = mutableListOf<AttemptChord>()
        trueChordSequence.forEachIndexed { i, chord ->
            val attemptChord = AttemptChord(chord=chord, hit=(chord==answerChordSequence[i]))
            attemptChords.add(attemptChord)
        }
        hasAnswered.value = true

        return Pair(attempt, attemptChords)
    }

    fun skipSequence(): Pair<Attempt, List<AttemptChord>> {
        val attempt = Attempt(
            difficulty=selectedDifficulty,
            key=selectedMusicalKey.name,
            length=selectedLength
        )
        val attemptChords = mutableListOf<AttemptChord>()
        trueChordSequence.forEach { chord ->
            val attemptChord = AttemptChord(chord=chord, hit=false)
            attemptChords.add(attemptChord)
        }
        generateNextSequence()
        return Pair(attempt, attemptChords)

    }

    fun generateNextSequence() {
        hasAnswered.value = false
        trueChordSequence = generator.getRandomChordSequence()
    }

}
