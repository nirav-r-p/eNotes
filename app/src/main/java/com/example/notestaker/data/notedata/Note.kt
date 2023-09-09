package com.example.notestaker.data.notedata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class Note (
    val title:String,
    val description:String,
    val editTime:String,
    val status:Boolean=false,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)