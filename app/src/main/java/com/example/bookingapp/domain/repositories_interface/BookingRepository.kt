package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Booking
import kotlinx.coroutines.flow.StateFlow

interface BookingRepository {

    fun getBookingsInfoByUserId(userId: String): StateFlow<List<Booking>>

    fun getBookingInfoById(id: String): StateFlow<Booking>

    suspend fun createBooking(booking: Booking): Void

    suspend fun deleteBookingById(id: String): Void
}