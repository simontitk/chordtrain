package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.RawRes
import com.example.chordtrain.db.MusicalKey


class ChordSequenceGenerator(
    private val musicalKey: MusicalKey,
    private val difficulty: String,
    private val sequenceLength: Int
) {
    private var possibleChords: List<String>? = null

    fun getPossibleChords(): List<String> {
        if( possibleChords == null) {
            val chords = mutableListOf(musicalKey.chord1, musicalKey.chord4, musicalKey.chord5)
            if (difficulty == "Medium") {
                chords.add(musicalKey.chord6)
                chords.add(musicalKey.chord2)
            }
            if (difficulty == "Hard") {
                chords.add(musicalKey.chord6)
                chords.add(musicalKey.chord2)
                chords.add(musicalKey.chord3)
                chords.add(musicalKey.chord7)
            }
            possibleChords = chords
        }
        return possibleChords!!
    }

    fun getRandomChordSequence(): List<String> {
        val chords = getPossibleChords()
        val randomChords: MutableList<String> = mutableListOf()
        for (i in 1..sequenceLength) {
            randomChords.add(chords.random())
        }
        return randomChords
    }
}