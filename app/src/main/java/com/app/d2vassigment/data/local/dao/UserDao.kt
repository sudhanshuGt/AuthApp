package com.app.d2vassigment.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.d2vassigment.data.local.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
}