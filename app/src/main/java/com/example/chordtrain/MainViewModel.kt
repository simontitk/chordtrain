package com.example.chordtrain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chordtrain.db.ChordTrainDatabase
import com.example.chordtrain.db.MusicalKey
import com.example.chordtrain.db.MusicalKeyDao


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val musicalKeyDao: MusicalKeyDao
    val allMusicalKeys: LiveData<List<MusicalKey>>
    val selectedLength = MutableLiveData<Int>(1)
    val selectedDifficulty = MutableLiveData<String>("Easy")
    val selectedMusicalKey: MutableLiveData<String>

    init {
        val database = ChordTrainDatabase.getDatabase(application)
        musicalKeyDao = database.musicalKeyDao()
        allMusicalKeys = musicalKeyDao.getAllMusicalKey()
        selectedMusicalKey = MutableLiveData(allMusicalKeys.value?.get(0)?.name ?: "C major")
    }
}
