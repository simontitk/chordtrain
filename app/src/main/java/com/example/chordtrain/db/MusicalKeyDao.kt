package com.example.chordtrain.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicalKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: MusicalKey)

    @Query("SELECT * FROM musical_key ORDER BY name DESC")
    fun getAllMusicalKey(): LiveData<List<MusicalKey>>
}
