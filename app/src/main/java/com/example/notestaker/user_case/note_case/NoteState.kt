package com.example.notestaker.user_case.note_case

import com.example.notestaker.localDataBase.notedata.Note
import com.example.notestaker.localDataBase.userdata.UserInfo


data class NoteState(
    var notes:List<Note> = emptyList(),
    val title:String="",
    val description:String="",
    val editTime:String="",
    val searchNote:String="",
    val isEditNote:Boolean=false,
    val status:Boolean=false,
    var owner:UserInfo=UserInfo(),
    val id:Int=0
)
