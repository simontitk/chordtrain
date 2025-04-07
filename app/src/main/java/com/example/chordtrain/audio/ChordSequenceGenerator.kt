package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import com.example.chordtrain.R
import com.example.chordtrain.db.MusicalKey


class ChordSequenceGenerator(private val context: Context, private val difficulty:String, private val key:MusicalKey, private val length:Int) {

    private fun rawResourceUri(@RawRes resId: Int): Uri {
        return Uri.parse("rawresource://${context.packageName}/$resId")
    }

    private fun getPath(thing:String):Int {
       return context.resources.getIdentifier(thing, "raw", context.packageName)
    }

    fun getChordSequence(): List<Uri> {
        val chords = mutableListOf(key.chord1, key.chord4, key.chord5)

        if (difficulty == "Medium") {
            chords.add(key.chord6)
            chords.add(key.chord3)
        }

        if (difficulty == "Hard") {
            chords.add(key.chord6)
            chords.add(key.chord3)
            chords.add(key.chord7)
            chords.add(key.chord2)
        }

        val randomChords: MutableList<String> = mutableListOf()

        for (i in 1..length) {
            randomChords.add(chords.random())
        }



        return randomChords.map { rawResourceUri(getPath(it)) }


    }
}