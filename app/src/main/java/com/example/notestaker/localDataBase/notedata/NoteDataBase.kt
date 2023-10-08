package com.example.notestaker.localDataBase.notedata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notestaker.localDataBase.userdata.UserDao
import com.example.notestaker.localDataBase.userdata.UserInfo

@Database(
    entities = [UserInfo::class,Note::class],
    version = 2
)
abstract class NoteDataBase:RoomDatabase(){
    abstract val dao: NotesDuo
    abstract val userDao:UserDao
}