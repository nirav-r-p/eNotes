package com.example.notestaker.localDataBase.notedata

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.example.notestaker.localDataBase.userdata.UserInfo

@Entity(
   foreignKeys = [
      ForeignKey(
         UserInfo::class,
         parentColumns = ["id"],
         childColumns = ["userId"],
          onDelete = ForeignKey.CASCADE
      )
   ]
)
data class Note (
    val title:String,
    val description:String,
    val editTime:String,
    val status:Boolean=false,
    val userId:Int,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)