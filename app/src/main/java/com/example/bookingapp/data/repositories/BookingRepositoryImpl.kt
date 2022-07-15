package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor() : BookingRepository {
    override fun getBookingsInfoByUserId(userId: Int): List<Booking> {
        TODO("Not yet implemented")
    }

    override fun getBookingInfoById(id: Int): Booking {
        TODO("Not yet implemented")
    }

    override fun createBooking(booking: Booking): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteBookingById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}