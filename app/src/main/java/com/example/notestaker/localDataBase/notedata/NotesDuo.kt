package com.example.notestaker.localDataBase.notedata

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

    @Query("Select * from Note ")
     fun getNotesList():List<Note>



    @Query("Update Note set status=:status where id==:id")
    suspend fun setPrivacyStatus(status:Boolean,id:Int)


}