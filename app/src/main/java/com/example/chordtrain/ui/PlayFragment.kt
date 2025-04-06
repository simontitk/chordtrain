package com.example.chordtrain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chordtrain.MainViewModel
import com.example.chordtrain.R

class PlayFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return  view
    }
}