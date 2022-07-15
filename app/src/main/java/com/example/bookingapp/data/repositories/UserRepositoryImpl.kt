package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUserInfoById(id: Int): User {
        TODO("Not yet implemented")
    }

    override fun registerUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(user: User): Boolean {
        TODO("Not yet implemented")
    }
}