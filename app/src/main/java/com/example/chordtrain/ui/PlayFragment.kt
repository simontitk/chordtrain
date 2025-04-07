package com.example.chordtrain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi

import com.example.chordtrain.MainViewModel
import com.example.chordtrain.R
import com.example.chordtrain.audio.ChordPlayer
import com.example.chordtrain.audio.ChordSequenceGenerator

@UnstableApi
class PlayFragment: Fragment() {

    private lateinit var player: ChordPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val chordSequenceGenerator = ChordSequenceGenerator(requireContext(), viewModel.selectedDifficulty.value!!, viewModel.selectedMusicalKey.value!!, viewModel.selectedLength.value!!)
        player = ChordPlayer(requireContext(), chordSequenceGenerator)

        val playButton: Button = view.findViewById(R.id.play_button)
        playButton.setOnClickListener {
            player.play()
        }

        return  view
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}