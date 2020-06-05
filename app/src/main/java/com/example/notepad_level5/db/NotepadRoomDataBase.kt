package com.example.notepad_level5.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notepad_level5.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotepadRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var INSTANCE: NotepadRoomDatabase? = null

        fun getDatabase(context: Context) : NotepadRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, NotepadRoomDatabase::class.java, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                                /*A callback has been added to the database builder. This gets
                                invoked when the database is built. Inside the callback the
                                onCreate has been overridden because this only gets called when
                                the database is created. Using let we check if the INSTANCE
                                is not null. If it’s not null then the note is added using
                                a Coroutine and the noteDao.
                                * */
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.noteDao().insertNote(Note("Title", Date(), ""))
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }

            return INSTANCE
        }
    }
}