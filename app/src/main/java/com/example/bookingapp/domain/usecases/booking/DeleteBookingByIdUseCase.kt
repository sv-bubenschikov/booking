package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import javax.inject.Inject

class DeleteBookingByIdUseCase @Inject constructor(
    private val bookingRepository: BookingRepository
) {

    suspend operator fun invoke(id: String) =
        bookingRepository.deleteBookingById(id)
}