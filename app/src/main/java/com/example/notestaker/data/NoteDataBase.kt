package com.example.notestaker.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDataBase:RoomDatabase(){
    abstract val dao:NotesDuo
}