package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.RawRes
import com.example.chordtrain.db.MusicalKey


class ChordSequenceGenerator(
    private val context: Context,
    private val musicalKey: MusicalKey,
    private val difficulty: String,
    private val sequenceLength: Int
) {

    private fun rawResourceUri(@RawRes resId: Int): Uri {
        return Uri.parse("android.resource://${context.packageName}/$resId")
    }


    private fun getChordId(chordName: String): Int {
        val normalizedChordName = chordName
            .lowercase()
            .replace(" ", "_")
            .replace("#", "_sharp")

        val id = context.resources.getIdentifier(normalizedChordName, "raw", context.packageName)

        if (id == 0) {
            Log.e("ChordDebug", "Resource not found after normalization: $normalizedChordName")
        }
        return id
    }


    fun getChordSequence(): List<Uri> {
        val chordNames = mutableListOf(musicalKey.chord1, musicalKey.chord4, musicalKey.chord5)

        if (difficulty == "Medium") {
            chordNames.add(musicalKey.chord6)
            chordNames.add(musicalKey.chord2)
        }

        if (difficulty == "Hard") {
            chordNames.add(musicalKey.chord6)
            chordNames.add(musicalKey.chord2)
            chordNames.add(musicalKey.chord3)
            chordNames.add(musicalKey.chord7)
        }

        val randomChords: MutableList<String> = mutableListOf()

        for (i in 1..sequenceLength) {
            randomChords.add(chordNames.random())
        }

        return randomChords.map { rawResourceUri(getChordId(it)) }
    }
}