package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Booking

interface BookingRepository {

    fun getBookingInfo(): List<Booking>

    fun getBookingInfoById(id: Int): Booking

    fun createBooking(booking: Booking): Boolean

    fun deleteBookingById(id: Int): Boolean
}