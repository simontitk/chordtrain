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
        player = ChordPlayer(requireContext(), ChordSequenceGenerator(requireContext()))

        val playButton: Button = view.findViewById(R.id.play_button)
        playButton.setOnClickListener {
            player.play()
        }

        val settings: TextView = view.findViewById(R.id.sth)
        viewModel.selectedMusicalKey.observe(viewLifecycleOwner) { newKey ->
            settings.text = "settings: $${viewModel.selectedLength.value} ${viewModel.selectedDifficulty.value} chords in ${newKey}"
        }
        viewModel.selectedDifficulty.observe(viewLifecycleOwner) { newDiff ->
            settings.text = "settings: $${viewModel.selectedLength.value} $newDiff chords in ${viewModel.selectedMusicalKey.value}"
        }
        viewModel.selectedLength.observe(viewLifecycleOwner) { newLength ->
            settings.text = "settings: $newLength ${viewModel.selectedDifficulty.value} chords in ${viewModel.selectedMusicalKey.value}"
        }

        return  view
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}