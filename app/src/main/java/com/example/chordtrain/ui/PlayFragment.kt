package com.example.chordtrain.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.example.chordtrain.viewmodels.MainViewModel
import com.example.chordtrain.viewmodels.PlayViewModel
import com.example.chordtrain.viewmodels.PlayViewModelFactory
import com.example.chordtrain.R
import com.example.chordtrain.audio.ChordPlayer
import com.example.chordtrain.enums.PlayResult

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

        repeat(mainViewModel.selectedLength.value ?: 0) { i ->

            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }
            val label = TextView(requireContext()).apply {
                text = "Chord ${i+1}: "
                textSize = 18f
            }
            val spinner = Spinner(requireContext())
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_spinner,
                playViewModel.getPossibleChords())
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            row.addView(label)
            row.addView(spinner)
            spinnerContainer.addView(row)
            spinnerList.add(spinner)
        }

        val checkButton: Button = view.findViewById(R.id.check_answer_button)
        checkButton.setOnClickListener {
            player.stop()
            val (result, attempt, attemptChords) = playViewModel.checkAnswer(spinnerList.map { it.selectedItem.toString() })
            runAnimation(result)
            mainViewModel.addAttempt(attempt, attemptChords)
        }

        val skipSequenceButton: Button = view.findViewById(R.id.skip_sequence_button)
        skipSequenceButton.setOnClickListener {
            player.stop()
            val (result, attempt, attemptChords) = playViewModel.skipSequence()
            runAnimation(result)
            mainViewModel.addAttempt(attempt, attemptChords)
        }

        val nextSequenceButton: Button = view.findViewById(R.id.next_sequence_button)
        nextSequenceButton.setOnClickListener {
            player.stop()
            playViewModel.generateNextSequence()
        }

        playViewModel.hasAnswered.observe(viewLifecycleOwner) { hasAnswered ->
            setButtonState(checkButton, !hasAnswered)
            setButtonState(skipSequenceButton, !hasAnswered)
            setButtonState(nextSequenceButton, hasAnswered)
        }
    }

    private fun runAnimation(result: PlayResult) {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val imageView = view?.findViewById<ImageView>(R.id.animated_image)

        val image = when (result) {
            PlayResult.SUCCESS -> R.drawable.train_green
            PlayResult.MIXED -> R.drawable.train_yellow
            PlayResult.FAILURE -> R.drawable.train_red
        }

        imageView?.setImageResource(image)

        val offScreenLeft = ((imageView?.width ?: 10000) * -1).toFloat()
        val offScreenRight = 2 * screenWidth.toFloat()
        val animation = ObjectAnimator.ofFloat(imageView, "translationX", offScreenLeft, offScreenRight)
        animation.duration = 1500
        animation.start()
    }

    private fun setButtonState(button: Button, active: Boolean) {
        if (active) {
            button.isEnabled = true
            button.alpha = 1f
            button.isClickable = true
        } else {
            button.isEnabled = false
            button.alpha = 0.5f
            button.isClickable = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}