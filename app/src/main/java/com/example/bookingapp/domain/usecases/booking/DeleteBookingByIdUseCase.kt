package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository

class DeleteBookingByIdUseCase(private val bookingRepository: BookingRepository) {
    operator fun invoke(id: Int): Boolean {
        return bookingRepository.deleteBookingById(id)
    }
}