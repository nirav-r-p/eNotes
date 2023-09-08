package com.example.notestaker.user_case

import com.example.notestaker.data.Note


data class NoteState(
    var notes:List<Note> = emptyList(),
    val title:String="",
    val description:String="",
    val editTime:String="",
    val searchNote:String="",
    val isEditNote:Boolean=false,
    val id:Int=-1
)
