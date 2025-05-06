package com.example.chordtrain.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chordtrain.audio.ChordSequenceGenerator
import com.example.chordtrain.db.Attempt
import com.example.chordtrain.db.AttemptChord
import com.example.chordtrain.db.MusicalKey
import com.example.chordtrain.enums.PlayResult

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

    fun checkAnswer(answerChordSequence: List<String>): Triple<PlayResult, Attempt, List<AttemptChord>> {
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
        val result = getResult((attemptChords))
        return Triple(result, attempt, attemptChords)
    }

    fun skipSequence(): Triple<PlayResult, Attempt, List<AttemptChord>> {
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
        return Triple(PlayResult.FAILURE, attempt, attemptChords)

    }

    private fun getResult(attemptChord: List<AttemptChord>): PlayResult {
        if (attemptChord.all { it.hit }) {
            return PlayResult.SUCCESS
        }
        else if (attemptChord.none { it.hit }) {
            return PlayResult.FAILURE
        }
        return PlayResult.MIXED
    }

    fun generateNextSequence() {
        hasAnswered.value = false
        trueChordSequence = generator.getRandomChordSequence()
    }

}
