package com.example.notestaker.model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestaker.data.Note
import com.example.notestaker.data.NotesDuo
import com.example.notestaker.user_case.NoteEvent
import com.example.notestaker.user_case.NoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel(
    private val dao:NotesDuo
):ViewModel() {
    private val dataFormat=SimpleDateFormat("HH:mm a", Locale.getDefault())

    private val _state= MutableStateFlow(NoteState())
    private val _list=
        dao.getNotesList().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        emptyList()
    )
    val state= combine(_state,_list){
            state,list->state.copy(
            notes = list,
    )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.DeleteNotes -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.HideAddBox->{
                viewModelScope.launch {
                    _state.update {
                        it.copy(isAddingNote = false)
                    }
                }
            }
            NoteEvent.ShowAddBox -> {
                _state.update {
                    it.copy(isAddingNote = true)
                }
            }

            NoteEvent.SaveNotes -> {
                 val currentTime = Date()
                val time:String=dataFormat.format(currentTime)
                val title:String=state.value.title
                val des:String=state.value.description
                val editTime:String=time

                if(title.isBlank()||des.isBlank()||editTime.isBlank()){
                    return
                }
                val note= Note(
                    title = title,
                    description = des,
                    editTime = editTime,
                )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        editTime = "",
                        isAddingNote = false
                    )
                }
            }
            NoteEvent.SearchNote->{
                val search = state.value.searchNote
                if(search.isBlank()){
                    return
                }
                viewModelScope.launch {
                    dao.getNoteByTitle(search)
                }

            }
            is NoteEvent.SetSearchNote -> {
                _state.update {
                    it.copy(searchNote = event.title)
                }
            }
            is NoteEvent.ShowEditBox->{
               _state.update {
                   it.copy(isAddingNote = true)
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
                        id = event.note.id
                    )
                }
            }
             NoteEvent.UpdateNote->{
                 val currentTime = Date()
                 val time:String=dataFormat.format(currentTime)
                val title:String=state.value.title
                val des:String=state.value.description
                val editTime:String=time
                val id:Int=state.value.id

                if(title.isBlank()||des.isBlank()||editTime.isBlank()){
                    return
                }
                val note= Note(
                    title = title,
                    description = des,
                    editTime = editTime,
                    id = id
                )
               viewModelScope.launch {
                   dao.upsertNote(note)
               }
                 _state.update {
                     it.copy(
                         title = "",
                         description = "",
                         editTime = "",
                         isAddingNote = false
                     )
                 }
            }
        }
    }
}