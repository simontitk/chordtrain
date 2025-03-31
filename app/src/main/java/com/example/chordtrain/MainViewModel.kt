package com.example.chordtrain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val musicalKeyDao: MusicalKeyDao
    val allMusicalKeys: LiveData<List<MusicalKey>>

    init {
        val database = ChordTrainDatabase.getDatabase(application)
        musicalKeyDao = database.musicalKeyDao()
        allMusicalKeys = musicalKeyDao.getAllMusicalKey()
    }
}
