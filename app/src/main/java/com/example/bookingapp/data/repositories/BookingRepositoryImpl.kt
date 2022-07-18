package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.BookingDate
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor() : BookingRepository {

    override fun getBookingsInfoByUserId(userId: Int) = MutableStateFlow(
        listOf(
            Booking(
                0,
                userId,
                Company(0, "Тензор", ""),
                Place(0, "Переговорная Hawaii", "Переговорная", "3", 8, emptyList()),
                BookingDate()
            )
        )
    )

    override fun getBookingInfoById(id: Int) = MutableStateFlow(
        Booking(
            id,
            0,
            Company(0, "Тензор", ""),
            Place(0, "Переговорная Hawaii", "Переговорная", "3", 8, emptyList()),
            BookingDate()
        )
    )

    override fun createBooking(booking: Booking): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteBookingById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}