package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import com.example.chordtrain.R

class ChordSequenceGenerator(private val context: Context) {

    private fun rawResourceUri(@RawRes resId: Int): Uri {
        return Uri.parse("rawresource://${context.packageName}/$resId")
    }

    //TODO: remove hardcoded sequence
    fun getChordSequence(): List<Uri> {
        return listOf(
            rawResourceUri(R.raw.a_minor_fixed),
            rawResourceUri(R.raw.g_major_fixed),
            rawResourceUri(R.raw.c_major_fixed)
        )
    }
}