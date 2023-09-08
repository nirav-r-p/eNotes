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
    private val _list= dao.getNotesList().stateIn(
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
                        id = id
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
//                val search = searchTitle
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
            }
            is NoteEvent.SetSearchNote -> {
                _state.update {
                    it.copy(searchNote = event.title)
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
            NoteEvent.ResetNoteState->{
                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        editTime = "",
                        isEditNote = false
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
        }
    }
}