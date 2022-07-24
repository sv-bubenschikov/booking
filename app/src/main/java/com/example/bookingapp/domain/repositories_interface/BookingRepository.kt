package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Booking
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

    fun getBookingsByUserId(userId: String): Flow<List<Booking>>

    fun getBookingInfoById(id: String): Flow<Booking>

    suspend fun createBooking(booking: Booking)

    suspend fun deleteBookingById(id: String)
}