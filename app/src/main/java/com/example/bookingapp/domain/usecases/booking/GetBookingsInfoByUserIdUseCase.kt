package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import javax.inject.Inject

class GetBookingsInfoByUserIdUseCase @Inject constructor(private val bookingRepository: BookingRepository) {

    operator fun invoke(userId: String) =
        bookingRepository.getBookingsInfoByUserId(userId)
}