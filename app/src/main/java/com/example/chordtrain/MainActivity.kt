package com.example.chordtrain

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        val keySpinner: Spinner = findViewById(R.id.chord_spinner)
        viewModel.allMusicalKeys.observe(this) { keys ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                keys.map { key -> key.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            keySpinner.adapter = adapter
        }

        val difficultySpinner: Spinner = findViewById(R.id.difficulty_spinner)
        val difficultyAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                listOf("Easy", "Medium", "Hard")
        )
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        difficultySpinner.adapter = difficultyAdapter


        val lengthRadioGroup: RadioGroup = findViewById(R.id.length_radiogroup)
        lengthRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedLength.value = when (checkedId) {
                R.id.length_option1 -> 1
                R.id.length_option2 -> 2
                R.id.length_option3 -> 3
                R.id.length_option4 -> 4
                else -> 1
            }
        }

        val lengthInfo: TextView = findViewById(R.id.length_info_text)
        viewModel.selectedLength.observe(this) { length ->
            lengthInfo.text = when (length) {
                1 -> "Ideal for deeply internalizing the sound of a chord"
                2 -> "Ideal for feeling the contrast between two chords"
                3 -> "Covers 99% of pop songs"
                4 -> "Test your music skills and your memory"
                else -> "Keep on chording"
            }
        }
    }
}
