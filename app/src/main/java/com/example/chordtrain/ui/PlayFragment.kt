package com.example.chordtrain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.example.chordtrain.viewmodels.MainViewModel
import com.example.chordtrain.viewmodels.PlayViewModel
import com.example.chordtrain.viewmodels.PlayViewModelFactory
import com.example.chordtrain.R
import com.example.chordtrain.audio.ChordPlayer

@UnstableApi
class PlayFragment: Fragment() {

    private lateinit var player: ChordPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val factory = PlayViewModelFactory(
            mainViewModel.selectedLength.value!!,
            mainViewModel.selectedDifficulty.value!!,
            mainViewModel.selectedMusicalKey.value!!)
        val playViewModel = ViewModelProvider(this, factory)[PlayViewModel::class.java]

        player = ChordPlayer(requireContext())

        val playButton: Button = view.findViewById(R.id.play_button)
        playButton.setOnClickListener {
            player.play(playViewModel.trueChordSequence)
        }

        val spinnerContainer: LinearLayout = view.findViewById(R.id.answer_spinner_container)
        val spinnerList = mutableListOf<Spinner>()

        repeat(mainViewModel.selectedLength.value ?: 0) {
            val spinner = Spinner(requireContext())
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_spinner,
                playViewModel.getPossibleChords())
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinnerContainer.addView(spinner)
            spinnerList.add(spinner)
        }

        val checkButton: Button = view.findViewById(R.id.check_answer_button)
        checkButton.setOnClickListener {
            player.stop()
            val (attempt, attemptChords) = playViewModel.checkAnswer(spinnerList.map { it.selectedItem.toString() })
            mainViewModel.addAttempt(attempt, attemptChords)
        }

        val skipSequenceButton: Button = view.findViewById(R.id.skip_sequence_button)
        skipSequenceButton.setOnClickListener {
            player.stop()
            playViewModel.skipSequence()
        }

        val nextSequenceButton: Button = view.findViewById(R.id.next_sequence_button)
        nextSequenceButton.setOnClickListener {
            player.stop()
            playViewModel.generateNextSequence()
        }

        playViewModel.hasAnswered.observe(viewLifecycleOwner) { hasAnswered ->
            checkButton.isEnabled = !hasAnswered
            skipSequenceButton.isEnabled = !hasAnswered
            nextSequenceButton.isEnabled = hasAnswered
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}