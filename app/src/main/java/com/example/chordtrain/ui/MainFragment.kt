package com.example.chordtrain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.chordtrain.MainViewModel
import com.example.chordtrain.R

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    

        val keySpinner: Spinner = view.findViewById(R.id.chord_spinner)
        viewModel.allMusicalKeys.observe(viewLifecycleOwner) { keys ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                keys.map { key -> key.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            keySpinner.adapter = adapter
        }

        keySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                val selectedMusicalKey = parentView.getItemAtPosition(position) as String
                val musicalKey = viewModel.allMusicalKeys.value?.find { it.name == selectedMusicalKey }
                viewModel.selectedMusicalKey.value = musicalKey
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val difficultySpinner: Spinner = view.findViewById(R.id.difficulty_spinner)
        val difficultyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listOf("Easy", "Medium", "Hard")
        )
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        difficultySpinner.adapter = difficultyAdapter

        difficultySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                val selectedDifficulty = parentView.getItemAtPosition(position) as String
                viewModel.selectedDifficulty.value =selectedDifficulty
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        val lengthRadioGroup: RadioGroup = view.findViewById(R.id.length_radiogroup)
        lengthRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedLength.value = when (checkedId) {
                R.id.length_option1 -> 1
                R.id.length_option2 -> 2
                R.id.length_option3 -> 3
                R.id.length_option4 -> 4
                else -> 1
            }
        }

        val lengthInfo: TextView = view.findViewById(R.id.length_info_text)
        viewModel.selectedLength.observe(viewLifecycleOwner) { length ->
            lengthInfo.text = when (length) {
                1 -> "Ideal for deeply internalizing the sound of a chord"
                2 -> "Ideal for feeling the contrast between two chords"
                3 -> "Covers 99% of pop songs"
                4 -> "Test your music skills and your memory"
                else -> "Keep on chording"
            }
        }

        val addFragmentButton: Button = view.findViewById(R.id.play_fragment_button)
        addFragmentButton.setOnClickListener {
            activity?.let {
                findNavController().navigate(R.id.action_main_to_play)
            }
        }
        return view
    }
}