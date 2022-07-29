package com.example.bookingapp.domain.usecases.booking

import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.usecases.user.GetCurrentUserUseCase
import javax.inject.Inject

class GetBookingsByUserIdAsyncUseCase @Inject constructor(private val bookingRepository: BookingRepository, private val getUser: GetCurrentUserUseCase) {

    suspend operator fun invoke() =
        getUser()!!.let { bookingRepository.getBookingsByUserIdAsync(it.uid) }
}