package com.example.notestaker.localDataBase.userdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    val userName:String="",
    val password:String="",
    val notePassword:String="",
    val isLogin:Boolean=false,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
