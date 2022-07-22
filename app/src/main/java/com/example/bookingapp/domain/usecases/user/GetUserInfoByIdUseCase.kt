package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.repositories_interface.UserRepository
import javax.inject.Inject

class GetUserInfoByIdUseCase @Inject constructor(private val userRepository: UserRepository, private val getCurrentUserRefUseCase: GetCurrentUserRefUseCase) {
    operator fun invoke() =
        getCurrentUserRefUseCase()?.let { userRepository.getUserInfoById(it.uid) }
}