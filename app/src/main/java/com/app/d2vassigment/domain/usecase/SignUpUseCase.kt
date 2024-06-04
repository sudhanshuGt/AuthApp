package com.app.d2vassigment.domain.usecase


import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(user: UserModel): Boolean {
        return userRepository.signUp(user)
    }
}