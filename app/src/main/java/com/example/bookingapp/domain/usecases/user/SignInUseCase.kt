package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, password: String): Task<AuthResult> {
        return userRepository.signInUser(email, password)
    }
}