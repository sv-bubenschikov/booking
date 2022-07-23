package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Booking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BookingRepository {

    fun getBookingsByUserId(userId: String): Flow<List<Booking>>

    fun getBookingInfoById(id: String): Flow<Booking>

    suspend fun createBooking(booking: Booking): Void

    suspend fun deleteBookingById(id: String): Void
}