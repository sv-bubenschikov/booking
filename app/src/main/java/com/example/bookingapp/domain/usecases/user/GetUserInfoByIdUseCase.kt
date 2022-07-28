package com.example.bookingapp.domain.usecases.user

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetUserInfoByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    operator fun invoke() = flow {
        emit(getCurrentUserUseCase())
    }.flatMapLatest { user ->
        user?.run { userRepository.getUserInfoById(uid) }
            ?: flowOf(User())
    }
}