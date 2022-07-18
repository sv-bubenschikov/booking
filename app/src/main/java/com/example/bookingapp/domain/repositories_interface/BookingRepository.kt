package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Booking
import kotlinx.coroutines.flow.StateFlow

interface BookingRepository {

    fun getBookingsInfoByUserId(userId: Int): StateFlow<List<Booking>>

    fun getBookingInfoById(id: Int): StateFlow<Booking>

    fun createBooking(booking: Booking): Boolean

    fun deleteBookingById(id: Int): Boolean
}