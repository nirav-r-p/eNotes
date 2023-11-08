package com.example.notestaker.model


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestaker.localDataBase.notedata.Note
import com.example.notestaker.userRepository.NoteRepository
import com.example.notestaker.user_case.note_case.NoteEvent
import com.example.notestaker.user_case.note_case.NoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel(
    private val noteRepository: NoteRepository
):ViewModel() {
    private val dataFormat=SimpleDateFormat("H:mm a", Locale.getDefault())
    private val _state= MutableStateFlow(NoteState())
    var state=_state.asStateFlow()
    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    noteRepository.deleteNotes(event.note)
                    getNotes()
                }
            }
            NoteEvent.SaveNotes -> {
                val currentTime = Date()
                val time:String=dataFormat.format(currentTime)
                val title:String=state.value.title
                val des:String=state.value.description
                val status=state.value.status
                val userId=state.value.owner
                Log.d("FKey", "onEvent: $userId")
                val editTime= if (state.value.isEditNote){
                    "edited at $time"
                }else{
                    "create at $time"
                }

                if(title.isBlank()||des.isBlank()||editTime.isBlank()){
                    return
                }

                val note=if(state.value.isEditNote) {
                    val id:Int=state.value.id
                    Note(
                        title = title,
                        description = des,
                        editTime = editTime,
                        id = id,
                        status = status,
                        userId = userId.id
                    )
                }else{
                    Note(
                        title = title,
                        description = des,
                        editTime = editTime,
                        userId = userId.id
                    )
                }
                viewModelScope.launch {
                    noteRepository.updateNotes(note)
                    getNotes()
                }
            }
            is NoteEvent.SetSearchNote -> {
                _state.update {
                    it.copy(
                        searchNote = event.title,
                    )
                }

            }
            is NoteEvent.SetDescription -> {
                _state.update {
                    it.copy(description = event.description)
                }
            }
            is NoteEvent.SetTitle -> {
               _state.update {
                   it.copy(title = event.title)
               }
            }
            is NoteEvent.SetEditNote->{
                _state.update {
                    it.copy(
                        title = event.note.title,
                        description = event.note.description,
                        editTime = event.note.editTime,
                        id = event.note.id,
                        status = event.note.status
                    )
                }
            }
            is NoteEvent.ClearNoteList->{
               _state.update {
                   it.copy(
                       notes = emptyList()
                   )
               }
            }
            NoteEvent.ResetNoteState->{
                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        editTime = "",
                        isEditNote = false,
                        status = false
                    )
                }
            }
            NoteEvent.IsEditMode->{
                _state.update {
                    it.copy(
                        isEditNote = true
                    )
                }
            }
            is NoteEvent.SetPrivate->{
                _state.update {
                    it.copy(
                        status = !event.status
                    )
                }
                viewModelScope.launch {
                    noteRepository.setPrivacy(state.value.status,event.id)
                }
            }
            is NoteEvent.SetUser->{
                viewModelScope.launch{
                    _state.update {
                        it.copy(
                            owner = event.user,
                        )
                    }
                    Log.d("set Own", "onEvent: ${state.value.owner} event ${event.user}")
                    getNotes()
                }
            }
            is NoteEvent.GetNotes->{
                getNotes()
            }
        }
    }
    private fun getNotes(){
        viewModelScope.launch {
            try{
                val notes:List<Note> =noteRepository.getNotes().filter { note -> note.userId==state.value.owner.id }
                Log.d("note List and owner", "onEvent: ${state.value.owner} $notes")
                _state.update {
                    it.copy(
                        notes = notes.sortedByDescending {t-> t.editTime }
                    )
                }
            }catch (e:Exception){
                Log.d("error", "onEvent: $e")
            }

        }
    }
}
