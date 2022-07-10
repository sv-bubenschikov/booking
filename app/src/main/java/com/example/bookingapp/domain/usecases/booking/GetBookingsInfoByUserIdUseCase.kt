package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.repositories_interface.BookingRepository

class GetBookingsInfoByUserIdUseCase(private val bookingRepository: BookingRepository) {
    operator fun invoke(userId: Int): List<Booking> {
        return bookingRepository.getBookingsInfoByUserId(userId)
    }
}