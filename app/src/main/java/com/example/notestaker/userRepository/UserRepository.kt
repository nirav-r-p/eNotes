package com.example.notestaker.userRepository

import com.example.notestaker.localDataBase.userdata.UserDao
import com.example.notestaker.localDataBase.userdata.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val myDao: UserDao
) {
    suspend fun getLoginUser():List<UserInfo> {
        return withContext(Dispatchers.IO) {
            myDao.getLoginUser()
        }
    }
    suspend fun logOutUser(id:Int){
        return withContext(Dispatchers.IO){
            myDao.setLogOut(id)
        }
    }
    suspend fun addUser(userInfo: UserInfo){
        return withContext(Dispatchers.IO){
            myDao.addUser(userInfo)
        }
    }
    suspend fun getAllUser():List<UserInfo>{
        return withContext(Dispatchers.IO){
            myDao.getAllUser()
        }
    }
    suspend fun setLogin(id: Int){
        myDao.setLogin(id = id)
    }
    suspend fun deleteUser(user:UserInfo){
        myDao.deleteUser(user)
    }
}