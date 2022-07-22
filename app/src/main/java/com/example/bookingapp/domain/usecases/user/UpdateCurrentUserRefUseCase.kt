package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class UpdateCurrentUserRefUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() =
        userRepository.updateCurrentUserRef()
}