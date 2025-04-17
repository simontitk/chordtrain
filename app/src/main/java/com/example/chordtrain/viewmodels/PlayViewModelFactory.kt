package com.example.chordtrain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chordtrain.db.MusicalKey

class PlayViewModelFactory(
    private val selectedLength: Int,
    private val selectedDifficulty: String,
    private val selectedMusicalKey: MusicalKey
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayViewModel(selectedLength, selectedDifficulty, selectedMusicalKey) as T
    }
}