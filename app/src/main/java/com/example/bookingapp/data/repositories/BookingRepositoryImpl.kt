package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.*
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import org.joda.time.LocalDate
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor() : BookingRepository {

    override fun getBookingsInfoByUserId(userId: String) = MutableStateFlow(
        listOf(
            Booking(
                userId,
                "Летняя практика Android. Обсуждение макета приложения",
                "place id",
                LocalDate.now().toDate().time,
                57600000,
                61200000
            )
        )
    )

    override fun getBookingInfoById(id: String) = MutableStateFlow(
        Booking(
            "",
            "Летняя практика Android. Обсуждение макета приложения",
            "place id",
            LocalDate.now().toDate().time,
            57600000,
            61200000
        )
    )

    override suspend fun createBooking(booking: Booking): Void {
        val postRef = FirebaseDatabase.getInstance().reference
            .child("Bookings")
        val newPostRef = postRef
            .push()
        booking.id = newPostRef.key!!
        return newPostRef
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