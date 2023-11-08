package com.example.notestaker.userRepository

import androidx.lifecycle.viewModelScope
import com.example.notestaker.localDataBase.notedata.Note
import com.example.notestaker.localDataBase.notedata.NotesDuo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteRepository(
    private val dao:NotesDuo
) {
     suspend fun getNotes():List<Note>{
        return withContext(Dispatchers.IO){ dao.getNotesList()}

    }
    suspend fun updateNotes(note: Note){
        withContext(Dispatchers.Default){
            dao.upsertNote(note)
        }
    }
    suspend fun deleteNotes(note: Note){
        withContext(Dispatchers.Default){
            dao.deleteNote(note)
        }
    }
    suspend fun setPrivacy(flag:Boolean,id:Int){
        withContext(Dispatchers.Default){
            dao.setPrivacyStatus(flag,id)
        }
    }
}