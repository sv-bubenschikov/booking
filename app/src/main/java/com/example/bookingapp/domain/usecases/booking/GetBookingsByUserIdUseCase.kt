package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookingsByUserIdUseCase @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val getCurrentUserRefUseCase: GetCurrentUserRefUseCase
) {

    operator fun invoke() = flow {
        emit(getCurrentUserRefUseCase())
    }.flatMapLatest { user ->
        user?.run { bookingRepository.getBookingsByUserId(uid) }
            ?: emptyFlow()
    }
}