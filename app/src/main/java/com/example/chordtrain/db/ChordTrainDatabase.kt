package com.example.chordtrain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MusicalKey::class, Attempt::class, AttemptChord::class],  // Add new tables here
    version = 2,
    exportSchema = false
)
abstract class ChordTrainDatabase : RoomDatabase() {
    abstract fun musicalKeyDao(): MusicalKeyDao
    abstract fun statisticsDao(): StatisticsDao
    abstract fun attemptDao(): AttemptDao

    companion object {
        @Volatile
        private var INSTANCE: ChordTrainDatabase? = null

        fun getDatabase(context: Context): ChordTrainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChordTrainDatabase::class.java,
                    "chordtrain_database"
                ).addCallback(object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val musicalKeyDao = getDatabase(context).musicalKeyDao()
                                val musicalKey = listOf(
                                    MusicalKey(
                                        name="A major",
                                        chord1="A major",
                                        chord2="B minor",
                                        chord3="C# minor",
                                        chord4="D major",
                                        chord5="E major",
                                        chord6="F# minor",
                                        chord7="G# dim"
                                    ),
                                    MusicalKey(
                                        name="Bb major",
                                        chord1="Bb major",
                                        chord2="C minor",
                                        chord3="D minor",
                                        chord4="Eb major",
                                        chord5="F major",
                                        chord6="G minor",
                                        chord7="A dim"
                                    ),
                                    MusicalKey(
                                        name="F major",
                                        chord1="F major",
                                        chord2="G minor",
                                        chord3="A minor",
                                        chord4="Bb major",
                                        chord5="C major",
                                        chord6="D minor",
                                        chord7="E dim"
                                    ),
                                    MusicalKey(
                                        name="G major",
                                        chord1="G major",
                                        chord2="A minor",
                                        chord3="B minor",
                                        chord4="C major",
                                        chord5="D major",
                                        chord6="E minor",
                                        chord7="F# dim"
                                    )
                                )
                                musicalKey.forEach { musicalKeyDao.insert(it) }
                            }
                        }
                    }).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
