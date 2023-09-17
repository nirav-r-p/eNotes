package com.example.notestaker.model


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestaker.data.notedata.Note
import com.example.notestaker.data.notedata.NotesDuo
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel(
    private val dao: NotesDuo
):ViewModel() {
    private val dataFormat=SimpleDateFormat("HH:mm a", Locale.getDefault())
    private val searchString= MutableStateFlow("")
    private val _state= MutableStateFlow(NoteState())
    private val _list=searchString.flatMapLatest { value -> when(value){
        ""->
        dao.getNotesList()
        else -> dao.getNoteByTitle(value)
    } }
        .stateIn(
                    viewModelScope, SharingStarted.WhileSubscribed(),
                    emptyList()
    )

    val state= combine(_state,_list){
            state,list->state.copy(
            notes = list.sortedByDescending { it.editTime },
    )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.SaveNotes -> {
                val currentTime = Date()
                val time:String=dataFormat.format(currentTime)
                val title:String=state.value.title
                val des:String=state.value.description
                val status=state.value.status
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
                        status = status
                    )
                }else{
                    Note(
                        title = title,
                        description = des,
                        editTime = editTime,
                    )
                }
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
            }
            NoteEvent.SearchNote->{
//                val search = state.value.searchNote
//                var list= emptyList<Note>()
//                if(search.isBlank()){
//                    return
//                }
//                viewModelScope.launch {
//                   list=dao.getNoteByTitle(search).first()
//                }
//                viewModelScope.coroutineContext.apply {
//                    _state.update {
//                        it.copy(notes = list)
//                    }
//                }
                val value=state.value.searchNote

                Log.d("Note List", "onEvent: ${_state.value.notes}")
            }
            is NoteEvent.SetSearchNote -> {
                _state.update {
                    it.copy(searchNote = event.title)
                }

                viewModelScope.launch {
                    searchString.value=event.title
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
                    dao.setPrivacyStatus(state.value.status,event.id)
                }
            }
        }
    }
}