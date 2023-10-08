package com.example.notestaker.localDataBase.userdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao{
    @Upsert
    suspend fun addUser(user:UserInfo)
    @Query("select * from UserInfo")
    fun getAllUser(): List<UserInfo>
    @Query("Select * from UserInfo where id==:id")
    suspend fun getUser(id:Int):UserInfo
    @Query("Select password from UserInfo where id==:id")
    suspend fun getPassword(id: Int):String
    @Query("Select * from UserInfo where isLogin==:flag")
    suspend fun getLoginUser(flag:Boolean=true):List<UserInfo>

    @Query("Update UserInfo set isLogin=1 where id==:id")
    suspend fun setLogin(id:Int)

    @Query("Update UserInfo set isLogin=0 where id==:id")
    suspend fun setLogOut(id:Int)
    @Delete
    suspend fun deleteUser(user: UserInfo)

}