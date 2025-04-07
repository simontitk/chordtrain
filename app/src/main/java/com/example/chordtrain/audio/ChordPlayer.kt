package com.example.chordtrain.audio

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

@UnstableApi
class ChordPlayer(context: Context, generator: ChordSequenceGenerator) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private var sourceSequence: ConcatenatingMediaSource

    init {
        val dataFactory = DefaultDataSource.Factory(context)
        val mediaFactory = ProgressiveMediaSource.Factory(dataFactory)
        val concatSource = ConcatenatingMediaSource()
        val chords = generator.getChordSequence()

        chords.forEach {
            val mediaSource = mediaFactory.createMediaSource(MediaItem.fromUri(it))
            concatSource.addMediaSource(mediaSource)
        }

        player.volume = 0.4f
        sourceSequence = concatSource
    }

    fun play() {
        player.setMediaSource(sourceSequence)
        player.prepare()
        player.playWhenReady = true
    }

    fun release() {
        player.release()
    }
}