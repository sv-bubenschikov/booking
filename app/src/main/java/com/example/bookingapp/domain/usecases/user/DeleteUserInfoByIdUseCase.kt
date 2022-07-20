package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class DeleteUserInfoByIdUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String) =
        userRepository.deleteUserInfoById(id)
}