package com.example.notestaker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class Note (
    val title:String,
    val description:String,
    val editTime:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)