package com.example.notestaker.user_case.userLogin

sealed interface UserEvent {
    data class SetUserName(val userName:String):UserEvent
    data class SetPassword(val password:String):UserEvent

    data class SetLoginStatus(val loginStatus:Boolean):UserEvent

    object SaveUser:UserEvent

    object DeleteUser:UserEvent

}