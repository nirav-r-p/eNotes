package com.example.notestaker.model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.notestaker.data.userdata.UserDao
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel(
    private val userDuo:UserDao
) :ViewModel(){
    private val _userState= MutableStateFlow(UserState())
    fun onEvent(event:UserEvent){
        when(event){
            UserEvent.DeleteUser -> TODO()
            UserEvent.SaveUser -> {
                TODO()
            }
            is UserEvent.SetLoginStatus -> {
                _userState.update {
                    it.copy(
                        isLogin = event.loginStatus
                    )
                }
            }
            is UserEvent.SetPassword -> {
                _userState.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            is UserEvent.SetUserName -> {
                _userState.update {
                    it.copy(
                        userName = event.userName
                    )
                }
            }
        }
    }
}