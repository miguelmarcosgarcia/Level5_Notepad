package com.example.notepad_level5.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notepad_level5.db.NoteRepository

/*
* The ViewModel for the MainActivity takes care of fetching the notepad from the repository
* and exposing it as a LiveData object which the activity can observe.
 */

class MainActivityViewModel (application: Application): AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application.applicationContext)
    val note = noteRepository.getNotepad()


}