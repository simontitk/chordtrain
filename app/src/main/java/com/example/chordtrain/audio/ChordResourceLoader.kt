package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import android.util.Log


class ChordResourceLoader(private val context: Context) {

    fun getChordUri(chordName: String): Uri {
        val normalizedChordName = chordName
            .lowercase()
            .replace(" ", "_")
            .replace("#", "_sharp")
            .replace("b", "_flat")

        val id = context.resources.getIdentifier(normalizedChordName, "raw", context.packageName)

        if (id == 0) {
            Log.e("ChordDebug", "Resource not found after normalization: $normalizedChordName")
        }

        return Uri.parse("android.resource://${context.packageName}/$id")
    }
}