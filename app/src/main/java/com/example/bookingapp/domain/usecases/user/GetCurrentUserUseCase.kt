package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): FirebaseUser? {
        return userRepository.getCurrentUser()
    }
}