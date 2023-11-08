package com.example.notestaker.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestaker.localDataBase.userdata.UserInfo
import com.example.notestaker.userRepository.UserRepository
import com.example.notestaker.user_case.userLogin.UserEvent
import com.example.notestaker.user_case.userLogin.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) :ViewModel(){
    private val _state= MutableStateFlow(UserState())
    val state:StateFlow<UserState> = _state.asStateFlow()
    private val _data = MutableLiveData<Resource<List<UserInfo>>>()
    val data: LiveData<Resource<List<UserInfo>>> get() = _data

    fun fetchData() {
        var l2:List<UserInfo>
        viewModelScope.launch {
            try {
                l2=repository.getAllUser()
                _state.update {
                    it.copy(
                        userList = l2
                    )
                }
            }catch (e:Exception){
                Log.d("Error", "fetchData: ${e.message}")
            }
        }
    }
    fun getLoginUser():Boolean{
        var l1:List<UserInfo>
        viewModelScope.launch {
           try {
               l1= repository.getLoginUser()
               _state.update {
                   it.copy(
                       loginUserList = l1
                   )
               }
               _data.value=Resource.Success(l1)
           } catch (e:  Exception){

           }

        }
        return state.value.loginUserList.isNotEmpty()
    }
    fun onEvent(event: UserEvent){
      when(event){
          is UserEvent.DeleteUser -> {
              viewModelScope.launch {
                  repository.deleteUser(event.user)
                  fetchData()
              }

          }
          is UserEvent.LogOut -> {
              viewModelScope.launch {
                  repository.logOutUser(event.userId)
                  _data.value=Resource.Success(emptyList<UserInfo>())
              }
          }
          UserEvent.SaveUser ->
          {
              val username=state.value.username
              val pass=state.value.password
              Log.d("auth", "onEvent: $username $pass")
              if (username.isBlank()||pass.isBlank()){
                  return
              }
              val user=UserInfo(
                  userName = username,
                  password = pass,
                  notePassword = pass,
                  isLogin = false
              )
              viewModelScope.launch {
                  repository.addUser(user)
                  fetchData()
              }

          }
          is UserEvent.SetLogin -> {
              viewModelScope.launch {
                  repository.setLogin(event.userId)
              }
          }
          is UserEvent.SetPassword -> {
              _state.update {
                  it.copy(
                     password = event.password
                  )
              }
          }
          is UserEvent.SetUserName -> {
              _state.update {
                  it.copy(
                      username = event.userName
                  )
              }
          }
      }
    }

}
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}



