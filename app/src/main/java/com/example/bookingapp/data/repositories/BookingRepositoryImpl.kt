package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.BookingDate
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor() : BookingRepository {

    override fun getBookingsInfoByUserId(userId: String) = MutableStateFlow(
        listOf(
            Booking(
                "",
                66666666,
                66666666,
                "Тензор",
                "companyID",
                "Переговорная Hawaii",
                "Обсуждение",
                66666666666666666,
                listOf("Oleg, Andrey"),
            )
        )
    )

    override fun getBookingInfoById(id: String) = MutableStateFlow(
        Booking(
            "",
            66666666,
            66666666,
            "Тензор",
            "companyID",
            "Переговорная Hawaii",
            "Обсуждение",
            66666666666666666,
            listOf("Oleg, Andrey"),
        )
    )

    override suspend fun createBooking(booking: Booking): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Bookings")
            .push()
            .setValue(booking)
            .await()
    }

    override suspend fun deleteBookingById(id: String): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Bookings")
            .child(id)
            .removeValue()
            .await()
    }
}