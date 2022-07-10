package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository

class UpdateUserInfoUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: User): Boolean {
        return userRepository.updateUserInfo(user)
    }
}