package com.example.notestaker.user_case.userLogin

import com.example.notestaker.localDataBase.userdata.UserInfo

sealed interface UserEvent {
    data class SetUserName(val userName:String):UserEvent
    data class SetPassword(val password:String):UserEvent

    data class SetLogin(val userId: Int):UserEvent



    data class LogOut(val userId:Int):UserEvent

    object SaveUser:UserEvent
    data class DeleteUser(val user:UserInfo):UserEvent

}