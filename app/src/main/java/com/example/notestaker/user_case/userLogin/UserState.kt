package com.example.notestaker.user_case.userLogin

import com.example.notestaker.localDataBase.userdata.UserInfo


data class UserState(
    val username:String="",
    val password:String="",
    var loginUserList:List<UserInfo> = emptyList(),
    var userList:List<UserInfo> = emptyList()
)
