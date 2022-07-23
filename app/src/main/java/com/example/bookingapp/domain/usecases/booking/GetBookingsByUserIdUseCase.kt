package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import javax.inject.Inject

class GetBookingsByUserIdUseCase @Inject constructor(private val bookingRepository: BookingRepository, private val getCurrentUserRefUseCase: GetCurrentUserRefUseCase) {
    operator fun invoke() =
        getCurrentUserRefUseCase()?.let { bookingRepository.getBookingsByUserId(it.uid) }
}