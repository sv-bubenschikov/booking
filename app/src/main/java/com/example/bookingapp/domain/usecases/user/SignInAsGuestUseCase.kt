package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class SignInAsGuestUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() {
        return userRepository.signInAsGuest()
    }
}