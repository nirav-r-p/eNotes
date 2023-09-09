package com.example.notestaker.data.notedata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDuo {
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("Select * from Note")
     fun getNotesList():Flow<List<Note>>

    @Query("Select * from Note where title like :searchTitle")
    fun getNoteByTitle(searchTitle:String):Flow<List<Note>>

    @Query("Select * from Note order by editTime asc")
    fun getNoteByLatestEditTime():Flow<List<Note>>

    @Query("Update Note set status=:status where id==:id")
    suspend fun setPrivacyStatus(status:Boolean,id:Int)


}