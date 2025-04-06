package com.example.chordtrain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val musicalKeyDao: MusicalKeyDao
    val allMusicalKeys: LiveData<List<MusicalKey>>
    val selectedLength = MutableLiveData<Int>(1)

    init {
        val database = ChordTrainDatabase.getDatabase(application)
        musicalKeyDao = database.musicalKeyDao()
        allMusicalKeys = musicalKeyDao.getAllMusicalKey()
    }
}
