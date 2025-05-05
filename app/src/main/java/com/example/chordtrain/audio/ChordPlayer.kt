package com.example.chordtrain.audio

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

@UnstableApi
class ChordPlayer(private val context: Context) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val chordResourceLoader = ChordResourceLoader(context)

@OptIn(UnstableApi::class)
fun play(chords: List<String>) {
        val dataFactory = DefaultDataSource.Factory(context)
        val mediaFactory = ProgressiveMediaSource.Factory(dataFactory)
        val concatSource = ConcatenatingMediaSource()

        chords.forEach {
            val chordUri = chordResourceLoader.getChordUri(it)
            val mediaSource = mediaFactory.createMediaSource(MediaItem.fromUri(chordUri))
            concatSource.addMediaSource(mediaSource)
        }

        player.setMediaSource(concatSource)
        player.prepare()
        player.playWhenReady = true
    }

    fun stop() {
        player.stop()
    }

    fun release() {
        player.release()
    }
}