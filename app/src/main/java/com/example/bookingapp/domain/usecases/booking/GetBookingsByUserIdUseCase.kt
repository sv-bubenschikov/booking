package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.usecases.user.GetCurrentUserUseCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookingsByUserIdUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {

    operator fun invoke() = flow {
        emit(getCurrentUserUseCase())
    }.flatMapLatest { user ->
        user?.run { bookingRepository.getBookingsByUserId(uid) }
            ?: emptyFlow()
    }
}