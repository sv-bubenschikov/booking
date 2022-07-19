package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String) =
        userRepository.registerUser(email, password)
}