package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignInAsGuestUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() =
        userRepository.signInAsGuest()
}