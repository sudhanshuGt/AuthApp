package com.app.d2vassigment.data.repository



import com.app.d2vassigment.data.local.dao.UserDao
import com.app.d2vassigment.data.local.entity.User
import com.app.d2vassigment.domain.model.UserModel
import com.app.d2vassigment.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {
    override suspend fun signUp(user: UserModel): Boolean {
        val existingUser = userDao.getUserByUsername(user.username)
        return if (existingUser == null) {
            userDao.insertUser(User(user.username, user.password))
            true
        } else {
            false
        }
    }

    override suspend fun login(username: String, password: String): UserModel? {
        val user = userDao.getUser(username, password)
        return user?.let { UserModel( it.username, it.password) }
    }
}