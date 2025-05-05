package com.example.chordtrain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chordtrain.db.Attempt
import com.example.chordtrain.db.AttemptChord
import com.example.chordtrain.db.AttemptDao
import com.example.chordtrain.db.AttemptWithChords
import com.example.chordtrain.db.ChordTrainDatabase
import com.example.chordtrain.db.MusicalKey
import com.example.chordtrain.db.MusicalKeyDao
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val musicalKeyDao: MusicalKeyDao
    private val attemptDao: AttemptDao
    val allMusicalKeys: LiveData<List<MusicalKey>>
    val allAttempts: LiveData<List<AttemptWithChords>>
    val selectedLength = MutableLiveData<Int>(1)
    val selectedDifficulty = MutableLiveData<String>("Easy")
    val selectedMusicalKey: MutableLiveData<MusicalKey>

    init {
        val database = ChordTrainDatabase.getDatabase(application)
        musicalKeyDao = database.musicalKeyDao()
        attemptDao = database.attemptDao()
        allMusicalKeys = musicalKeyDao.getAllMusicalKey()
        allAttempts = attemptDao.getAllAttempts()
        selectedMusicalKey = MutableLiveData(allMusicalKeys.value?.get(0))
    }

    fun addAttempt(attempt: Attempt, attemptChords: List<AttemptChord>) {
        viewModelScope.launch {
            attemptDao.insertAttemptWithChords(attempt, attemptChords)
        }
    }
}
