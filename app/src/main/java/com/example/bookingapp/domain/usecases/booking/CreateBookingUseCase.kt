package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import javax.inject.Inject

class CreateBookingUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(booking: Booking) =
        bookingRepository.createBooking(booking)
}