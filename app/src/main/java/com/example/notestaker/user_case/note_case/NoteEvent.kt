package com.example.notestaker.user_case.note_case

import com.example.notestaker.localDataBase.notedata.Note
import com.example.notestaker.localDataBase.userdata.UserInfo

sealed interface NoteEvent{
    object SaveNotes: NoteEvent
    object IsEditMode: NoteEvent
    data class SetTitle(val title:String): NoteEvent
    data class SetDescription(val description: String): NoteEvent
    data class DeleteNotes(val note: Note): NoteEvent
    data class SetSearchNote(val title:String): NoteEvent
    data class SetEditNote(val note: Note): NoteEvent
    object ResetNoteState: NoteEvent
    data class SetUser(val user:UserInfo): NoteEvent
    object ClearNoteList:NoteEvent
    object GetNotes:NoteEvent
    data class SetPrivate(val status:Boolean,val id:Int): NoteEvent

}