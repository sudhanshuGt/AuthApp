package com.app.d2vassigment.domain.repository

import com.app.d2vassigment.domain.model.UserModel

interface UserRepository {
    suspend fun signUp(user: UserModel): Boolean
    suspend fun login(username: String, password: String): UserModel?
}