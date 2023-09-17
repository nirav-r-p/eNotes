package com.example.notestaker.user_case.userLogin

//import com.example.notestaker.data.userdata.UserInfo

data class UserState(
    val userName:String="",
    val password:String="",
    val isLogin:Boolean=false,
    val id:Int=0
)
