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
                                        name="B major",
                                        chord1="B major",
                                        chord2="C# minor",
                                        chord3="D# minor",
                                        chord4="E major",
                                        chord5="F# major",
                                        chord6="G# minor",
                                        chord7="A# dim"
                                    ),
                                    MusicalKey(
                                        name="C major",
                                        chord1="C major",
                                        chord2="D minor",
                                        chord3="E minor",
                                        chord4="F major",
                                        chord5="G major",
                                        chord6="A minor",
                                        chord7="B dim"
                                    ),
                                    MusicalKey(
                                        name="Db major",
                                        chord1="Db major",
                                        chord2="Eb minor",
                                        chord3="F minor",
                                        chord4="Gb major",
                                        chord5="Ab major",
                                        chord6="Bb minor",
                                        chord7="C dim"
                                    ),
                                    MusicalKey(
                                        name="D major",
                                        chord1="D major",
                                        chord2="E minor",
                                        chord3="F# minor",
                                        chord4="G major",
                                        chord5="A major",
                                        chord6="B minor",
                                        chord7="C# dim"
                                    ),
                                    MusicalKey(
                                        name="Eb major",
                                        chord1="Eb major",
                                        chord2="F minor",
                                        chord3="G minor",
                                        chord4="Ab major",
                                        chord5="Bb major",
                                        chord6="C minor",
                                        chord7="D dim"
                                    ),
                                    MusicalKey(
                                        name="E major",
                                        chord1="E major",
                                        chord2="F# minor",
                                        chord3="G# minor",
                                        chord4="A major",
                                        chord5="B major",
                                        chord6="C# minor",
                                        chord7="D# dim"
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
                                        name="Gb major",
                                        chord1="Gb major",
                                        chord2="Ab minor",
                                        chord3="Bb minor",
                                        chord4="Cb major",
                                        chord5="Db major",
                                        chord6="Eb minor",
                                        chord7="F dim"
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
                                    ),
                                    MusicalKey(
                                        name="Ab major",
                                        chord1="Ab major",
                                        chord2="Bb minor",
                                        chord3="C minor",
                                        chord4="Db major",
                                        chord5="Eb major",
                                        chord6="F minor",
                                        chord7="G dim"
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
