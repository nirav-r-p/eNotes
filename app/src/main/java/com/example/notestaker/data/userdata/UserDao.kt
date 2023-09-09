package com.example.notestaker.data.userdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
//@Dao
//interface UserDao{
//    @Upsert
//    suspend fun addUser(user:UserInfo)
//    @Query("Select * from UserInfo where id==:id")
//    suspend fun getUser(id:Int):UserInfo
//    @Query("Select password from UserInfo where id==:id")
//    suspend fun getPassword(id: Int):String
//    @Delete
//    suspend fun deleteUser(user: UserInfo)
//
//}