package com.app.d2vassigment.domain.usecase


import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(username: String, password: String): UserModel? {
        return userRepository.login(username, password)
    }
}