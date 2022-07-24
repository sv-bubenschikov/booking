package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = withContext(Dispatchers.Default) {
        userRepository.logout()
    }
}