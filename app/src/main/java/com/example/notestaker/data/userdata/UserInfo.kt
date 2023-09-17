package com.example.notestaker.data.userdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    val userName:String,
    val password:String,
    val isLogin:Boolean,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
