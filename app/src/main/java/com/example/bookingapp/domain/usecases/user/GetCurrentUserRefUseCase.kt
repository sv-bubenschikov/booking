package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class GetCurrentUserRefUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() =
        userRepository.getCurrentUserRef()
}