package com.example.notestaker.user_case

import com.example.notestaker.data.Note

sealed interface NoteEvent{
    object SaveNotes:NoteEvent
    object ShowAddBox:NoteEvent
    object HideAddBox:NoteEvent
    data class ShowEditBox(val editNote: Note):NoteEvent
    data class SetTitle(val title:String):NoteEvent
    data class SetDescription(val description: String):NoteEvent
    data class DeleteNotes(val note: Note):NoteEvent
    object SetEditTime:NoteEvent
    data class SetSearchNote(val title:String):NoteEvent
    object SearchNote:NoteEvent
}