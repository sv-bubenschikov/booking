package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.repositories_interface.BookingRepository

class GetBookingInfoByIdUseCase(private val bookingRepository: BookingRepository) {
    operator fun invoke(id: Int): Booking {
        return bookingRepository.getBookingInfoById(id)
    }
}